package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mediscreen-mdb", url = "${mediscreen-mdb.url}")
public interface MicroserviceNotesProxy {
    //    @GetMapping(value = "/PatHistoryList")
//    List<PatientHistoryBean> patientHistoryList();
//
//    @GetMapping(value = "/PatHistory/lastname/{lastname}")
//    PatientHistoryBean getPatientHistoryByLastname(@PathVariable("lastname") String lastname);
//
    @GetMapping(value = "/PatHistory/id/{patId}")
    PatientHistoryBean getPatientByPatId(@PathVariable Long patId);

//    @PostMapping(value = "/PatHistory/add")
//    PatientHistoryBean addPatientHistory(PatientHistoryBean patientHistory);
//
//    @DeleteMapping(value = "/PatHistory/delete/{patId}")
//    PatientHistoryBean deletePatientById(@PathVariable Long patId);
//
//    @PutMapping(value = "/PatHistory/update/{patId}")
//    PatientHistoryBean updatePatientById(@PathVariable Long patId, @RequestBody PatientHistoryBean patientToUpdate);
}
