package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import com.ocr.mediscreen_assess.service.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getAssessmentByLastname(@Valid @RequestParam("lastname") String lastname) {
        return patientHistoryService.getAssessmentByLastname(lastname);
    }


    @GetMapping(value = "Assess/id/{patId}")
    public String getAssessmentById(@Valid @PathVariable Long patId) {
        return patientHistoryService.getAssessmentById(patId);
    }


    @PostMapping(value = "/PatHistory/add")
    public PatientHistory addPatientHistory(@RequestBody PatientHistory patientHistory) {
        PatientHistory patientAdded = microserviceNotesProxy.addPatientHistory(patientHistory);
        return patientAdded;
    }

    @RequestMapping(value = "PatHistory/update", method = RequestMethod.PUT)
    public PatientHistory updatePatientByLastname(@RequestParam("lastname") String lastname, @RequestBody PatientHistory patientToUpdate) {
        PatientHistory patientHistory = microserviceNotesProxy.updatePatientByLastname(lastname, patientToUpdate);
        return patientHistory;
    }

    @PutMapping(value = "/PatHistory/update/{patId}")
    public PatientHistory updatePatientById(@PathVariable Long patId, @RequestBody PatientHistory patientToUpdate) {
        PatientHistory patientHistory = microserviceNotesProxy.updatePatientById(patId, patientToUpdate);
        return patientHistory;
    }

    @RequestMapping(value = "PatHistory/delete", method = RequestMethod.DELETE)
    public PatientHistory deletePatientByLastname(@RequestParam("lastname") String lastname) {
        PatientHistory patientHistory = microserviceNotesProxy.deletePatientByLastname(lastname);
        return patientHistory;
    }

    @DeleteMapping(value = "/PatHistory/delete/{patId}")
    public PatientHistory deletePatientById(@PathVariable Long patId) {
        PatientHistory patientHistory = microserviceNotesProxy.deletePatientById(patId);
        return patientHistory;
    }

    @GetMapping(value = "PatHistory/id/{patId}")
    public PatientHistory getPatHistoryById(@Valid @PathVariable Long patId) {
        return microserviceNotesProxy.getPatientByPatId(patId);
    }


}
