package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.PatientBean;
import com.ocr.mediscreen_assess.model.PatientHistoryBean;
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


    public List<PatientHistoryBean> getPatientHistoryList() {
        List<PatientHistoryBean> patientList = microserviceNotesProxy.patientHistoryList();
        return patientList;
    }

    public String getAssessmentByLastname(String lastname) {
        PatientBean patientBean = microservicePatientProxy.getPatientByLastname(lastname).orElse(new PatientBean());
        String riskLevel = "Unknown";
        // Attention aux triggerWords français et anglais.
        PatientHistoryBean patientHistoryBean = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        Integer nbTrigger = Math.toIntExact(triggerWordsService.findAllTriggers().getTriggerList().stream()
                .filter(trigger -> patientHistoryBean.getNotes().toLowerCase().contains(trigger.toLowerCase())).count());

        Integer age = getAge(lastname);
        String gender = patientBean.getGender();

        if (noneMatch(nbTrigger)) {
            riskLevel = "None";
        } else if (borderline(nbTrigger, age)) {
            riskLevel = "Borderline";
        } else if (danger(nbTrigger, age, gender)) {
            riskLevel = "In Danger";
        } else if (earlyOnset(nbTrigger, age, gender)) {
            riskLevel = "Early onset";
        }
        return diabetesAssessment(patientBean, riskLevel);
    }


    public String getAssessmentById(Long patId) {
//        PatientHistory patientHistory = microserviceNotesProxy.getPatientByPatId(patId);
        PatientBean patientBean = microservicePatientProxy.getPatientById(patId).orElse(new PatientBean());
        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
        // Attention aux triggerWords français et anglais.
        PatientHistoryBean patientHistoryBean = microserviceNotesProxy.getPatientByPatId(patId);
        Integer nbTrigger = Math.toIntExact(triggerWordsService.findAllTriggers().getTriggerList().stream()
                .filter(trigger -> patientHistoryBean.getNotes().toLowerCase().contains(trigger.toLowerCase())).count());

        Integer age = getAge(patientBean.getLastname());
        String gender = patientBean.getGender();

        if (noneMatch(nbTrigger)) {
            riskLevel = "None";
        } else if (borderline(nbTrigger, age)) {
            riskLevel = "Borderline";
        } else if (danger(nbTrigger, age, gender)) {
            riskLevel = "In Danger";
        } else if (earlyOnset(nbTrigger, age, gender)) {
            riskLevel = "Early onset";
        }
        return diabetesAssessment(patientBean, riskLevel);
    }


    private String diabetesAssessment(PatientBean patientBean, String riskLevel) {
        int age = getAge(patientBean.getLastname());
        return patientBean.getFirstname() + " " + patientBean.getLastname() + " (age " + age +
                ") diabetes assessment is: " + riskLevel;
    }

    public Integer getAge(String lastname) {
        Optional<PatientBean> patient = microservicePatientProxy.getPatientByLastname(lastname);
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