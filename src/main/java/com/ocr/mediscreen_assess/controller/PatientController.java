package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.PatientBean;
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
    public List<PatientBean> getPatientList() {
        List<PatientBean> patientBeanList = microservicePatientProxy.patientList();
        return patientBeanList;
    }

    @GetMapping(value = "Patient/{lastname}")
    Optional<PatientBean> getPatientByLastname(@Valid @PathVariable("lastname") String lastname) {
        Optional<PatientBean> patient = microservicePatientProxy.getPatientByLastname(lastname);
        return patient;
    }


    @PostMapping(value = "/Patient/add")
    PatientBean addPatient(@RequestBody PatientBean patientBean) {
        PatientBean patientBeanAdded = microservicePatientProxy.addPatient(patientBean);
        return patientBeanAdded;
    }

    @PutMapping(value = "/Patient/update/{lastname}")
    PatientBean updatePatient(@PathVariable String lastname, @RequestBody PatientBean patientBeanToUpdate) {
        PatientBean patientBean = microservicePatientProxy.updatePatient(lastname, patientBeanToUpdate);
        return patientBean;
    }

    @DeleteMapping(value = "/Patient/delete/{lastname}")
    PatientBean deletePatient(@PathVariable String lastname) {
        PatientBean patientBean = microservicePatientProxy.deletePatient(lastname);
        return patientBean;
    }
}
