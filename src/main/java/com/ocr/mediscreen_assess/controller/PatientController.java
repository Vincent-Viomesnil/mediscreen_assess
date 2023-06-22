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
    public Optional<Patient> getPatientByLastname(@Valid @PathVariable("lastname") String lastname) {
        Optional<Patient> patient = microservicePatientProxy.getPatientByLastname(lastname);
        return patient;
    }

    @GetMapping(value = "Patient/id/{id}")
    public Optional<Patient> getPatientById(@Valid @PathVariable("id") Long id) {
        Optional<Patient> patient = microservicePatientProxy.getPatientById(id);
        return patient;
    }

    @PostMapping(value = "/Patient/add")
    public Patient addPatient(@RequestBody Patient patient) {
        Patient patientAdded = microservicePatientProxy.addPatient(patient);
        return patientAdded;
    }

    @RequestMapping(value = "Patient/update", method = RequestMethod.PUT)
    public Patient updatePatientByLastname(@RequestParam("lastname") String lastname, @RequestBody Patient patientToUpdate) {
        Patient patient = microservicePatientProxy.updatePatientByLastname(lastname, patientToUpdate);
        return patient;
    }

    @RequestMapping(value = "Patient/delete", method = RequestMethod.DELETE)
    public Patient deletePatientByLastname(@RequestParam("lastname") String lastname) {
        Patient patient = microservicePatientProxy.deletePatientByLastname(lastname);
        return patient;
    }

    @PutMapping(value = "/Patient/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patientToUpdate) {
        Patient patient = microservicePatientProxy.updatePatientById(id, patientToUpdate);
        return patient;
    }

    @DeleteMapping(value = "/Patient/delete/{id}")
    public Patient deletePatient(@PathVariable Long id) {
        Patient patient = microservicePatientProxy.deletePatientById(id);
        return patient;
    }
}
