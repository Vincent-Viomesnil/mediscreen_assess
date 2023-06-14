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

    public String getAssessmentByLastname(String lastname) {
//        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
        // Attention aux triggerWords français et anglais.

        if (noneMatch(lastname)) {
            riskLevel = "None";
        } else if (borderline(lastname)) {
            riskLevel = "Borderline";
        } else if (danger(lastname)) {
            riskLevel = "In Danger";
        } else if (earlyOnset(lastname)) {
            riskLevel = "Early onset";
        }
        return diabetesAssessment(patient.get(), riskLevel);
    }


    public String getAssessmentById(Long patId) {
//        PatientHistory patientHistory = microserviceNotesProxy.getPatientByPatId(patId);
        Optional<Patient> patient = microservicePatientProxy.getPatientById(patId);
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
        // Attention aux triggerWords français et anglais.

        if (noneMatch(patient.get().getLastname())) {
            riskLevel = "None";
        } else if (borderline(patient.get().getLastname())) {
            riskLevel = "Borderline";
        } else if (danger(patient.get().getLastname())) {
            riskLevel = "In Danger";
        } else if (earlyOnset(patient.get().getLastname())) {
            riskLevel = "Early onset";
        }
        return diabetesAssessment(patient.get(), riskLevel);
    }


    private String diabetesAssessment(Patient patient, String riskLevel) {
        int age = getAge(patient.getLastname());
        return patient.getFirstname() + " " + patient.getLastname() + " (age " + age +
                ") diabetes assessment is: " + riskLevel;
    }

    public Integer getAge(String lastname) {
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        LocalDate birthdate = patient.get().getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        return period.getYears();
    }

    private boolean noneMatch(String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        return triggerWordsService.findAll().getTriggerList().stream()
                .noneMatch(trigger -> patientHistory.getNotes().contains(trigger));
    }


    private boolean borderline(String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        return triggerWordsService.findAll().getTriggerList().stream()
                .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 2 && (getAge(lastname)) > 30;
    }


    private boolean danger(String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Optional<Patient> patientList = microservicePatientProxy.getPatientByLastname(lastname);
        return ((getAge(lastname) < 30) &&
                (patientList.get().getGender().equals("M")
                        &&
                        triggerWordsService.findAll().getTriggerList().stream()
                                .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 3)
                ||
                (patientList.get().getGender().equals("F")
                        &&
                        triggerWordsService.findAll().getTriggerList().stream()
                                .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 4))
                ||
                ((getAge(lastname) > 30) && (triggerWordsService.findAll().getTriggerList().stream()
                        .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 6));
    }

    private boolean earlyOnset(String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Optional<Patient> patientList = microservicePatientProxy.getPatientByLastname(lastname);
        return ((getAge(lastname) < 30) &&
                (patientList.get().getGender().equals("M")
                        &&
                        triggerWordsService.findAll().getTriggerList().stream()
                                .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 5)
                ||
                (patientList.get().getGender().equals("F")
                        &&
                        triggerWordsService.findAll().getTriggerList().stream()
                                .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() == 7))
                ||
                ((getAge(lastname) > 30) && (triggerWordsService.findAll().getTriggerList().stream()
                        .filter(trigger -> patientHistory.getNotes().contains(trigger)).count() >= 8));
    }
}