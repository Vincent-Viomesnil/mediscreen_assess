package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen-mdb", url = "localhost:8082")
public interface MicroserviceNotesProxy {
    @GetMapping(value = "/PatHistoryList")
    List<PatientHistory> patientHistoryList();

    @GetMapping(value = "/PatHistory/lastname/{lastname}")
    PatientHistory getPatientHistoryByLastname(@PathVariable("lastname") String lastname);

    @PostMapping(value = "/PatHistory/add")
    ResponseEntity<Object> addPatient(@Valid @RequestBody PatientHistory patientHistory);

    @PutMapping(value = "/PatHistory/update/{lastname}")
    PatientHistory updatePatient(@PathVariable String lastname, @RequestBody PatientHistory patientToUpdate);

    @DeleteMapping(value= "/PatHistory/delete/{lastname}")
    PatientHistory deletePatient(@PathVariable String lastname);

    @GetMapping(value = "/PatHistory/id/{patId}")
    List<PatientHistory> getPatientByPatId(@PathVariable Long patId);

//    @GetMapping(value = "/PatHistory/{firstname}")
//    Optional<PatientHistory> getPatientByFirstname(String firstname);

//    @GetMapping(value = "assess/{firstname}")
//    Optional<PatientHistory> getPatientByFirstname(@Valid @RequestParam("firstname") String firstname);
}
