//package com.ocr.mediscreen_assess.controller;
//
//import com.ocr.mediscreen_assess.model.PatientBean;
//import com.ocr.mediscreen_assess.model.PatientHistoryBean;
//import com.ocr.mediscreen_assess.service.PatientHistoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//public class PatientHistoryController {
//
//    @Autowired
//    private PatientHistoryService patientHistoryService;
//
//
//    @RequestMapping(value = "/PatHistoryList", method = RequestMethod.GET)
//    public List<PatientHistoryBean> patientHistoryList() {
//        return patientHistoryService.getPatientHistoryList();
//    }
//
//    @RequestMapping(value = "Assess", method = RequestMethod.GET)
//    public String getAssessmentByLastname(@RequestParam("lastname") String lastname) {
//        return patientHistoryService.getAssessmentByLastname(lastname);
//    }
//
//    @GetMapping(value = "Assess/id/{patId}")
//    public String getAssessmentById(@PathVariable Long patId) {
//        return patientHistoryService.getAssessmentById(patId);
//    }
//
//    @GetMapping(value = "/PatHistory/id/{patId}")
//    PatientHistoryBean getPatientByPatId(@PathVariable Long patId) {
//        return patientHistoryService.getPatientByPatId(patId);
//    }
//
//    @PostMapping(value = "/PatHistory/add")
//    PatientHistoryBean addPatientHistory(@RequestBody PatientHistoryBean patientHistory) {
//        return patientHistoryService.addPatientHistory(patientHistory);
//    }
//
//    @DeleteMapping(value = "/PatHistory/delete/{patId}")
//    PatientHistoryBean deletePatientById(@PathVariable Long patId) {
//        return patientHistoryService.deletePatientById(patId);
//    }
//
//    @PutMapping(value = "/PatHistory/update/{patId}")
//    PatientHistoryBean updatePatientById(@PathVariable Long patId, @RequestBody PatientHistoryBean patientToUpdate) {
//        return patientHistoryService.updatePatientById(patId, patientToUpdate);
//    }
//
//    @GetMapping(value = "/Patients")
//    List<PatientBean> patientList() {
//        return patientHistoryService.getPatientList();
//    }
//
//    @PostMapping(value = "/Patient/add")
//    PatientBean addPatient(@RequestBody PatientBean patient) {
//        return patientHistoryService.addPatient(patient);
//    }
//
//    @DeleteMapping(value = "/Patient/delete/{id}")
//    PatientBean deletePatient(@PathVariable Long id) {
//        return patientHistoryService.deletePatient(id);
//    }
//
//}
