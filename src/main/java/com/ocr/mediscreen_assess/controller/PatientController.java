package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class PatientController {

    private final MicroservicePatientProxy microservicePatientProxy;


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
    Patient addPatient(@RequestBody Patient patient) {
        Patient patientAdded = microservicePatientProxy.addPatient(patient);
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
