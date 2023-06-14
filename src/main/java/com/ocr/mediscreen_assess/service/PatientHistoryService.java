package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class PatientHistoryService {


    @Autowired
    private TriggerWordsService triggerWordsService;


    private final MicroserviceNotesProxy microserviceNotesProxy;
    private final MicroservicePatientProxy microservicePatientProxy;


    public PatientHistoryService(MicroserviceNotesProxy microserviceNotesProxy, MicroservicePatientProxy microservicePatientProxy) {
        this.microserviceNotesProxy = microserviceNotesProxy;
        this.microservicePatientProxy = microservicePatientProxy;
    }


    public List<PatientHistory> getPatientHistoryList() {
        List<PatientHistory> patientList = microserviceNotesProxy.patientHistoryList();
        return patientList;
    }

    public String getPatientById(Long patId) {
        List<PatientHistory> patientHistoryList = microserviceNotesProxy.getPatientByPatId(patId);
        Optional<Patient> patientList = microservicePatientProxy.getPatientById(patId);

        for (PatientHistory patientHistory : patientHistoryList) {
            boolean containTriggerWord = triggerWordsService.findAll().getTriggerList().stream()
                    .anyMatch(trigger -> patientHistory.getNotes().contains(trigger));
            if (containTriggerWord) {
                return "diabetes assessment not good : To check";
            }
        }
        return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(patientList.get().getLastname()) +
                ") diabetes assessment is : None";
    }

    public String getPatientByLastname(String lastname) {
        PatientHistory patientHistoryList = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Optional<Patient> patientList = microservicePatientProxy.getPatientByLastname(lastname);
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut

        boolean containTriggerWord = triggerWordsService.findAll().getTriggerList().stream()
                .noneMatch(trigger -> patientHistoryList.getNotes().contains(trigger));
        boolean borderline = triggerWordsService.findAll().getTriggerList().stream()
                .filter(trigger -> patientHistoryList.getNotes().contains(trigger))
                .distinct().count() == 2;
        boolean danger = triggerWordsService.findAll().getTriggerList().stream()
                .filter(trigger -> patientHistoryList.getNotes().contains(trigger)).count() == 3;

        if (containTriggerWord) {
            riskLevel = "None";
        } else if (borderline) {
            riskLevel = "Borderline";
        } else if (danger) {
            riskLevel = "In Danger";
        }


        String assessment;
        switch (riskLevel) {
            case "None":
                assessment = "None";
                break;
            case "Borderline":
                assessment = "Borderline";
                break;
            case "In Danger":
                assessment = "In Danger";
                break;
//            case "Early onset":
//                assessment = "Early onset";
//                break;
            default:
                assessment = "Unknown";
                break;
        }

        if (assessment.equals("None")) {
            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
                    ") diabetes assessment is: None";
        } else if (assessment.equals("Borderline")) {
            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
                    ") diabetes assessment is: Borderline";
        } else if (assessment.equals("In Danger")) {
            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
                    ") diabetes assessment is: In Danger";
        }

        return riskLevel;
    }

    public Integer getAge(String lastname) {
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        LocalDate birthdate = patient.get().getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        return period.getYears();
    }
}
