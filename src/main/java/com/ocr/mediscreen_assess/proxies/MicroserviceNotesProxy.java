package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "mediscreen-mdb", url = "localhost:8082")
public interface MicroserviceNotesProxy {
    @GetMapping(value = "/PatHistory")
    List<PatientHistory> patientHistoryList();

    @GetMapping(value = "/PatHistory/{lastname}")
    List<PatientHistory> getPatientHistoryByLastname(@PathVariable("lastname") String lastname);

    @PostMapping(value = "/PatHistory/add")
    ResponseEntity<Object> addPatient(@Valid @RequestBody PatientHistory patientHistory);

    @PutMapping(value = "/PatHistory/update/{lastname}")
    PatientHistory updatePatient(@PathVariable String lastname, @RequestBody PatientHistory patientToUpdate);

    @DeleteMapping(value= "/PatHistory/delete/{lastname}")
    PatientHistory deletePatient(@PathVariable String lastname);

}
