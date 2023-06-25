package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.model.TriggerWords;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PatientHistoryService {


    private TriggerWords triggerWords = new TriggerWords();


    @Autowired
    private MicroserviceNotesProxy microserviceNotesProxy;
    @Autowired
    private MicroservicePatientProxy microservicePatientProxy;


    public List<PatientHistory> getPatientHistoryList() {
        List<PatientHistory> patientList = microserviceNotesProxy.patientHistoryList();
        return patientList;
    }

    public String getAssessmentByLastname(String lastname) {
        Patient patient = microservicePatientProxy.getPatientByLastname(lastname).orElse(new Patient());
        String riskLevel = "Unknown";
        // Attention aux triggerWords français et anglais.
        PatientHistory patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        if (patientHistory == null || patient == null) {
            System.out.println("patientHistory is null:" + patientHistory + "patient is null :" + patient);
            return "patientHistory is null";
        }

        Integer nbTrigger = Math.toIntExact(triggerWords.getTriggerList().stream()
                .filter(trigger -> patientHistory.getNotes().toLowerCase().contains(trigger.toLowerCase())).count());

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


    public String getAssessmentById(Long patId) {
//        PatientHistory patientHistory = microserviceNotesProxy.getPatientByPatId(patId);
        Patient patient = microservicePatientProxy.getPatientById(patId).orElse(new Patient());
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
        // Attention aux triggerWords français et anglais.
        PatientHistory patientHistory = microserviceNotesProxy.getPatientByPatId(patId);

        if (patientHistory == null) {
            System.out.println("patientHistory is null:" + patientHistory + "patient is null :" + patient);

            return "patientHistory is null";
        }
        Integer nbTrigger = Math.toIntExact(triggerWords.getTriggerList().stream()
                .filter(trigger -> patientHistory.getNotes().toLowerCase().contains(trigger.toLowerCase())).count());

        Integer age = getAge(patient.getLastname());
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


    private String diabetesAssessment(Patient patient, String riskLevel) {
        int age = getAge(patient.getLastname());
        return patient.getFirstname() + " " + patient.getLastname() + " (age " + age +
                ") diabetes assessment is: " + riskLevel;
    }

    public Integer getAge(String lastname) {
        Patient patient = microservicePatientProxy.getPatientByLastname(lastname).orElse(new Patient());
        LocalDate birthdate = patient.getBirthdate();
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