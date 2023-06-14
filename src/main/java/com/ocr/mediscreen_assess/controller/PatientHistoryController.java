package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import com.ocr.mediscreen_assess.service.TriggerWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class PatientHistoryController {

    @Autowired
    private TriggerWordsService triggerWordsService;

    private final MicroserviceNotesProxy microserviceNotesProxy;
    private final MicroservicePatientProxy microservicePatientProxy;

    public PatientHistoryController(MicroserviceNotesProxy microserviceNotesProxy, MicroservicePatientProxy microservicePatientProxy) {
        this.microserviceNotesProxy = microserviceNotesProxy;
        this.microservicePatientProxy = microservicePatientProxy;
    }


    @RequestMapping(value="/PatHistoryList", method = RequestMethod.GET)
    public List<PatientHistory> patientHistoryList() {
        List<PatientHistory> patientList = microserviceNotesProxy.patientHistoryList();
        return patientList;
    }

//    @GetMapping("/PatHistory/{lastname}")
//    List<PatientHistory> getPatientHistoryByLastname(@PathVariable("lastname") String lastname) {
//        List<PatientHistory> patientHistory = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
//        return patientHistory;
//    }


    //Method which verify if there is 3 triggersWords for a Patient, filter by lastname
//    @RequestMapping(value = "Assess", method = RequestMethod.GET)
//    String getPatientByLastname(@Valid @RequestParam("lastname") String lastname){
//        List<PatientHistory> patient = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
//        String resultat = "diabetes assessment is: Warning";
//        if (patient.stream().anyMatch(patientHistory ->
//                Arrays.stream(patientHistory.getNotes().split("\\s+"))
//                        .filter(note -> triggerWordsService.findAll() != null)
//                        .distinct()
//                        .count() >= 3
//        )) {
//                return resultat;
//            }
//        return "There is no pb";
//        }

    @RequestMapping(value = "Assess", method = RequestMethod.GET)
    String getPatientByLastname(@Valid @RequestParam("lastname") String lastname) {
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

//    @RequestMapping(value = "Assess", method = RequestMethod.GET)
//    String getPatientByLastname(@Valid @RequestParam("lastname") String lastname) {
//        List<PatientHistory> patientHistoryList = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
//        Optional<Patient> patientList = microservicePatientProxy.getPatientByLastname(lastname);
//        String riskLevel = "Unknown"; // Niveau de risque initial par défaut
//
//
//        boolean containTriggerWord = triggerWordsService.findAll().getTriggerList().stream()
//                .noneMatch(trigger -> patientHistoryList.getNotes().contains(trigger));
//        boolean borderline = triggerWordsService.findAll().getTriggerList().stream()
//                .filter(trigger -> patientHistoryList.get(0).getNotes().contains(trigger))
//                .distinct().count() == 2;
//        boolean danger = triggerWordsService.findAll().getTriggerList().stream()
//                .filter(trigger -> patientHistoryList.get(0).getNotes().contains(trigger)).count() > 2;
//
//        if (containTriggerWord) {
//            riskLevel = "None";
//        } else if (borderline) {
//            riskLevel = "Borderline";
//        } else if (danger) {
//            riskLevel = "In Danger";
//        }
//
//        String assessment;
//        switch (riskLevel) {
//            case "None":
//                assessment = "None";
//                break;
//            case "Borderline":
//                assessment = "Borderline";
//                break;
//            case "In Danger":
//                assessment = "Danger";
//                break;
////            case "Early onset":
////                assessment = "Early onset";
////                break;
//            default:
//                assessment = "Unknown";
//                break;
//        }
//
//        if (assessment.equals("None")) {
//            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
//                    ") diabetes assessment is: None";
//        } else if (assessment.equals("BorderLine")) {
//            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
//                    ") diabetes assessment is: BorderLine";
//            } else if (assessment.equals("Danger")) {
//            return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
//                    ") diabetes assessment is: Danger";
//        }
//        return riskLevel;
//    }

// Ferguson == unknown. TestNone = None
    // Rees == None etBailey aussi



//    @RequestMapping(value = "Assess", method = RequestMethod.GET)
//    String getPatientByLastname(@Valid @RequestParam("lastname") String lastname) {
//        List<PatientHistory> patientHistoryList = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
//        Optional<Patient> patientList = microservicePatientProxy.getPatientByLastname(lastname);
//
//        for (PatientHistory patientHistory : patientHistoryList) {
//            String riskLevel = "riskLevel";
//            switch (riskLevel) {
//                case "None":
//                    boolean containTriggerWord = triggerWordsService.findAll().getTriggerList().stream()
//                            .noneMatch(trigger -> patientHistory.getNotes().contains(trigger));
//                    return patientList.get().getFirstname() + " " + patientList.get().getLastname() + " (age " + getAge(lastname) +
//                            ") diabetes assessment is : None";
//                break;
//                case "Borderline":
//                    // Traitement pour risque limité
//                    break;
//                case "In Danger":
//                    // Traitement pour danger
//                    break;
//                case "Early onset":
//                    // Traitement pour apparition précoce
//                    break;
//                default:
//                    // Traitement par défaut si le niveau de risque ne correspond à aucun des cas précédents
//                    break;
//
//
//                //        if (patient.stream().filter(patientHistory -> patientHistory.getNotes().contains("Poids")).count() == 1) {
//
//            }
//
//        }
//    }


    @GetMapping(value = "Assess/id/{patId}")
    String getPatientById(@Valid @PathVariable Long patId) {
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


    //risque limité (Borderline) - Le dossier du patient contient deux déclencheurs et
    //le patient est âgé de plus de 30 ans,
    //Patient: Test TestBorderline (age 73) diabetes assessment is: Borderline
//    @RequestMapping(value = "Assess", method = RequestMethod.GET)
//    String getBorderline(@Valid @RequestParam("lastname") String lastname){
//        List<PatientHistory> patient = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
//        String resultat = "diabetes assessment is: Warning";
//        if (patient.stream().anyMatch(patientHistory ->
//                Arrays.stream(patientHistory.getNotes().split("\\s+"))
//                        .filter(note -> triggerWordsService.findAll() != null)
//                        .distinct()
//                        .count() >= 3
//        )) {
//            return resultat;
//        }
//        return "There is no pb";
//    }

    @PostMapping(value = "/PatHistory/add")
    public ResponseEntity<Object> addPatient(@RequestBody PatientHistory patientHistory) {
        ResponseEntity<Object> patientAdded = microserviceNotesProxy.addPatient(patientHistory);
        return patientAdded;
    }

    @PutMapping(value = "/PatHistory/update/{lastname}")
    PatientHistory updatePatient(@PathVariable String lastname, @RequestBody PatientHistory patientToUpdate) {
        PatientHistory patientHistory = microserviceNotesProxy.updatePatient(lastname, patientToUpdate);
        return patientHistory;
    }

    @DeleteMapping(value = "/PatHistory/delete/{lastname}")
    PatientHistory deletePatient(@PathVariable String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.deletePatient(lastname);
        return patientHistory;
    }


    public Integer getAge(String lastname) {
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        LocalDate birthdate = patient.get().getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        return period.getYears();
    }
}
