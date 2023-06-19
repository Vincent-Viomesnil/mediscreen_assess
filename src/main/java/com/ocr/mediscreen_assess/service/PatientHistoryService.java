package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Patient patient = microservicePatientProxy.getPatientByLastname(lastname).orElse(new Patient());
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
        // Attention aux triggerWords français et anglais.
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Integer nbTrigger = Math.toIntExact(triggerWordsService.findAll().getTriggerList().stream()
                .filter(trigger -> patientHistory.getNotes().toLowerCase().contains(trigger.toLowerCase())).count());
        //ToLowerCase

        Integer age = getAge(lastname);
        String gender = patient.getGender();

        if (noneMatch(nbTrigger)) {
            riskLevel = "None";
        } else if (borderline(nbTrigger, age)) {
            riskLevel = "Borderline";
        } else if (danger(nbTrigger, age, gender)) {
            riskLevel = "In Danger";
        } else if (earlyOnset(nbTrigger, age, gender)) {
            riskLevel = "Early onset";
        }
        return diabetesAssessment(patient, riskLevel);
    }
    //Factorisation => externalisation


//    public String getAssessmentById(Long patId) {
////        PatientHistory patientHistory = microserviceNotesProxy.getPatientByPatId(patId);
//        Optional<Patient> patient = microservicePatientProxy.getPatientById(patId);
//        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
//        // Attention aux triggerWords français et anglais.
//
//        if (noneMatch(patient.get().getLastname())) {
//            riskLevel = "None";
//        } else if (borderline(patient.get().getLastname())) {
//            riskLevel = "Borderline";
//        } else if (danger(patient.get().getLastname())) {
//            riskLevel = "In Danger";
//        } else if (earlyOnset(patient.get().getLastname())) {
//            riskLevel = "Early onset";
//        }
//        return diabetesAssessment(patient.get(), riskLevel);
//    }


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

    private boolean noneMatch(Integer nbTrigger) {
        return nbTrigger == 0;
    }


    private boolean borderline(Integer nbTrigger, Integer age) {
        return nbTrigger == 2 && age > 30;
    }


    private boolean danger(Integer nbTrigger, Integer age, String gender) {

        return (age < 30) && (gender.equals("M") && nbTrigger == 3)
                ||
                (gender.equals("F") && nbTrigger == 4)
                ||
                (age > 30 && nbTrigger == 6);
    }

    private boolean earlyOnset(Integer nbTrigger, Integer age, String gender) {
        return (age < 30 && gender.equals("M") && nbTrigger == 5)
                ||
                (gender.equals("F") && nbTrigger == 7)
                ||
                (age > 30 && nbTrigger >= 8);
    }
}