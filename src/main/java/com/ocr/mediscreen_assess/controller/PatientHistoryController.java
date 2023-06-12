package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.service.TriggerWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class PatientHistoryController {

    @Autowired
    private TriggerWordsService triggerWordsService;

    private final MicroserviceNotesProxy microserviceNotesProxy;

    public PatientHistoryController(MicroserviceNotesProxy microserviceNotesProxy) {
        this.microserviceNotesProxy = microserviceNotesProxy;
    }

    @RequestMapping("/PatHistory")
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
        List<PatientHistory> patient = microserviceNotesProxy.getPatientHistoryByLastname(lastname);
        String resultat = "diabetes assessment is: Warning";

        for (PatientHistory patientHistory : patient) {
            boolean containTriggerWord = triggerWordsService.findAll().getTriggerList().stream()
                    .anyMatch(trigger -> patientHistory.getNotes().contains(trigger));
            //        if (patient.stream().filter(patientHistory -> patientHistory.getNotes().contains("Poids")).count() == 1) {
            if (containTriggerWord) {
                System.out.println("triggerWordsService.findAll(): " + triggerWordsService.findAll().toString());
                return resultat;
            }
        }
            return "There is no pb";
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
    PatientHistory updatePatient(@PathVariable String lastname, @RequestBody PatientHistory patientToUpdate){
        PatientHistory patientHistory= microserviceNotesProxy.updatePatient(lastname, patientToUpdate);
        return patientHistory;
    }

    @DeleteMapping(value= "/PatHistory/delete/{lastname}")
    PatientHistory deletePatient(@PathVariable String lastname){
        PatientHistory patientHistory = microserviceNotesProxy.deletePatient(lastname);
        return patientHistory;
    }

}
