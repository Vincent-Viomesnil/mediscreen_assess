package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ocr.mediscreen_assess.service.PatientService;

import java.util.List;


@RestController
public class PatientController {

//    @Autowired
//    private PatientService patientService;

    private final MicroservicePatientProxy microservicePatientProxy;

    public PatientController(MicroservicePatientProxy microservicePatientProxy) {
        this.microservicePatientProxy = microservicePatientProxy;
    }
    @RequestMapping("/Patients")
    public List<Patient> getPatientList() {
        List<Patient> patientList = microservicePatientProxy.patientList();
        return patientList;

    }

}
