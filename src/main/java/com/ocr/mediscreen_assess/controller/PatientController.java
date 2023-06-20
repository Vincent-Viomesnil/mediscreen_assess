package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import com.ocr.mediscreen_assess.service.PatientService;
import com.ocr.mediscreen_assess.service.TriggerWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class PatientController {

    private final MicroservicePatientProxy microservicePatientProxy;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TriggerWordsService triggerWordsService;

    public PatientController(MicroservicePatientProxy microservicePatientProxy) {
        this.microservicePatientProxy = microservicePatientProxy;
    }

    @RequestMapping(value = "/Patients", method = RequestMethod.GET)
    public List<Patient> getPatientList() {
        List<Patient> patientList = microservicePatientProxy.patientList();
        return patientList;
    }

    @GetMapping(value = "Patient/{lastname}")
    Optional<Patient> getPatientByLastname(@Valid @PathVariable("lastname") String lastname) {
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        return patient;
    }


    @PostMapping(value = "/Patient/add")
    public ResponseEntity<Object> addPatient(@RequestBody Patient patientHistory) {
        ResponseEntity<Object> patientAdded = microservicePatientProxy.addPatient(patientHistory);
        return patientAdded;
    }

    @PutMapping(value = "/Patient/update/{lastname}")
    Patient updatePatient(@PathVariable String lastname, @RequestBody Patient patientToUpdate) {
        Patient patient = microservicePatientProxy.updatePatient(lastname, patientToUpdate);
        return patient;
    }

    @DeleteMapping(value = "/Patient/delete/{lastname}")
    Patient deletePatient(@PathVariable String lastname) {
        Patient patient = microservicePatientProxy.deletePatient(lastname);
        return patient;
    }
}
