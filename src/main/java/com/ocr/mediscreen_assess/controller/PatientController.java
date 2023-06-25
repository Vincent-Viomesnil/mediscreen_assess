//package com.ocr.mediscreen_assess.controller;
//
//import com.ocr.mediscreen_assess.model.PatientBean;
//import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//
//
//@Controller
//public class PatientController {
//
//    private final MicroservicePatientProxy microservicePatientProxy;
//
//
//    public PatientController(MicroservicePatientProxy microservicePatientProxy) {
//        this.microservicePatientProxy = microservicePatientProxy;
//    }
//
//    @RequestMapping(value = "/Patients", method = RequestMethod.GET)
//    public List<PatientBean> getPatientList() {
//        List<PatientBean> patientList = microservicePatientProxy.patientList();
//        return patientList;
//    }
//
//    @GetMapping(value = "Patient/{lastname}")
//    public Optional<PatientBean> getPatientByLastname(@Valid @PathVariable("lastname") String lastname) {
//        Optional<PatientBean> patient = microservicePatientProxy.getPatientByLastname(lastname);
//        return patient;
//    }
//
//    @GetMapping(value = "Patient/id/{id}")
//    public Optional<PatientBean> getPatientById(@Valid @PathVariable Long id) {
//        Optional<PatientBean> patient = microservicePatientProxy.getPatientById(id);
//        return patient;
//    }
//
//    @PostMapping(value = "/Patient/add")
//    public PatientBean addPatient(@RequestBody PatientBean patient) {
//        PatientBean patientAdded = microservicePatientProxy.addPatient(patient);
//        return patientAdded;
//    }
//
//    @RequestMapping(value = "Patient/update", method = RequestMethod.PUT)
//    public PatientBean updatePatientByLastname(@RequestParam("lastname") String lastname, @RequestBody PatientBean patientToUpdate) {
//        PatientBean patient = microservicePatientProxy.updatePatientByLastname(lastname, patientToUpdate);
//        return patient;
//    }
//
//    @RequestMapping(value = "Patient/delete", method = RequestMethod.DELETE)
//    public PatientBean deletePatientByLastname(@RequestParam("lastname") String lastname) {
//        PatientBean patient = microservicePatientProxy.deletePatientByLastname(lastname);
//        return patient;
//    }
//
//    @PutMapping(value = "/Patient/update/{id}")
//    public PatientBean updatePatient(@PathVariable Long id, @RequestBody PatientBean patientToUpdate) {
//        PatientBean patient = microservicePatientProxy.updatePatientById(id, patientToUpdate);
//        return patient;
//    }
//
//    @DeleteMapping(value = "/Patient/delete/{id}")
//    public PatientBean deletePatient(@PathVariable Long id) {
//        PatientBean patient = microservicePatientProxy.deletePatientById(id);
//        return patient;
//    }
//}
