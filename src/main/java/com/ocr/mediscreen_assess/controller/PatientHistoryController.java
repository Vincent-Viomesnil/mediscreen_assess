package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.PatientHistoryBean;
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
    public List<PatientHistoryBean> patientHistoryList() {
        return patientHistoryService.getPatientHistoryList();
    }

    @RequestMapping(value = "Assess", method = RequestMethod.GET)
    String getAssessmentByLastname(@Valid @RequestParam("lastname") String lastname) {
        return patientHistoryService.getAssessmentByLastname(lastname);
    }


    @GetMapping(value = "Assess/id/{patId}")
    String getAssessmentById(@Valid @PathVariable Long patId) {
        return patientHistoryService.getAssessmentById(patId);
    }


    @PostMapping(value = "/PatHistory/add")
    public PatientHistoryBean addPatient(@RequestBody PatientHistoryBean patientHistoryBean) {
        PatientHistoryBean patientAdded = microserviceNotesProxy.addPatient(patientHistoryBean);
        return patientAdded;
    }

    @PutMapping(value = "/PatHistory/update/{lastname}")
    PatientHistoryBean updatePatient(@PathVariable String lastname, @RequestBody PatientHistoryBean patientToUpdate) {
        PatientHistoryBean patientHistoryBean = microserviceNotesProxy.updatePatient(lastname, patientToUpdate);
        return patientHistoryBean;
    }

    @DeleteMapping(value = "/PatHistory/delete/{lastname}")
    PatientHistoryBean deletePatient(@PathVariable String lastname) {
        PatientHistoryBean patientHistoryBean = microserviceNotesProxy.deletePatient(lastname);
        return patientHistoryBean;
    }

    @GetMapping(value = "PatHistory/id/{patId}")
    PatientHistoryBean getAssessmentByPatId(@Valid @PathVariable Long patId) {
        return microserviceNotesProxy.getPatientByPatId(patId);
    }


}
