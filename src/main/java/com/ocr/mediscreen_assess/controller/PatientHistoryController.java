package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import com.ocr.mediscreen_assess.service.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class PatientHistoryController {

    private final MicroserviceNotesProxy microserviceNotesProxy;
    private final MicroservicePatientProxy microservicePatientProxy;

    @Autowired
    private PatientHistoryService patientHistoryService;

    public PatientHistoryController(MicroserviceNotesProxy microserviceNotesProxy, MicroservicePatientProxy microservicePatientProxy) {
        this.microserviceNotesProxy = microserviceNotesProxy;
        this.microservicePatientProxy = microservicePatientProxy;
    }


    @RequestMapping(value = "/PatHistoryList", method = RequestMethod.GET)
    public List<PatientHistory> patientHistoryList() {
        return patientHistoryService.getPatientHistoryList();
    }

    @RequestMapping(value = "Assess", method = RequestMethod.GET)
    String getAssessmentByLastname(@Valid @RequestParam("lastname") String lastname) {
        return patientHistoryService.getAssessmentByLastname(lastname);
    }


//    @GetMapping(value = "Assess/id/{patId}")
//    String getAssessmentById(@Valid @PathVariable Long patId) {
//       return patientHistoryService.getAssessmentById(patId);
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

    @GetMapping(value = "PatHistory/id/{patId}")
    PatientHistory getAssessmentByPatId(@Valid @PathVariable Long patId) {
        return microserviceNotesProxy.getPatientByPatId(patId);
    }


}
