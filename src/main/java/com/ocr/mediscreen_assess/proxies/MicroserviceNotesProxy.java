package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "mediscreen-mdb", url = "localhost:8082")
public interface MicroserviceNotesProxy {
    @GetMapping(value = "/PatHistoryList")
    List<PatientHistoryBean> patientHistoryList();

    @GetMapping(value = "/PatHistory/lastname/{lastname}")
    PatientHistoryBean getPatientHistoryByLastname(@PathVariable("lastname") String lastname);

    @PostMapping(value = "/PatHistory/add")
    PatientHistoryBean addPatient(@Valid @RequestBody PatientHistoryBean patientHistoryBean);

    @PutMapping(value = "/PatHistory/update/{lastname}")
    PatientHistoryBean updatePatient(@PathVariable String lastname, @RequestBody PatientHistoryBean patientToUpdate);

    @DeleteMapping(value = "/PatHistory/delete/{lastname}")
    PatientHistoryBean deletePatient(@PathVariable String lastname);

    @GetMapping(value = "/PatHistory/id/{patId}")
    PatientHistoryBean getPatientByPatId(@PathVariable Long patId);

//    @GetMapping(value = "/PatHistory/{firstname}")
//    Optional<PatientHistory> getPatientByFirstname(String firstname);

//    @GetMapping(value = "assess/{firstname}")
//    Optional<PatientHistory> getPatientByFirstname(@Valid @RequestParam("firstname") String firstname);
}
