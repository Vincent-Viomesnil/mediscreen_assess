package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "mediscreen-mdb", url = "localhost:8082")
public interface MicroserviceNotesProxy {
    @GetMapping(value = "/PatHistoryList")
    List<PatientHistory> patientHistoryList();

    @GetMapping(value = "/PatHistory/lastname/{lastname}")
    PatientHistory getPatientHistoryByLastname(@PathVariable("lastname") String lastname);

    @PostMapping(value = "/PatHistory/add")
    PatientHistory addPatientHistory(@Valid @RequestBody PatientHistory patientHistory);

    @RequestMapping(value = "PatHistory/update", method = RequestMethod.PUT)
    PatientHistory updatePatientByLastname(@RequestParam("lastname") String lastname, @RequestBody PatientHistory patientToUpdate);

    @RequestMapping(value = "PatHistory/delete", method = RequestMethod.DELETE)
    PatientHistory deletePatientByLastname(@RequestParam("lastname") String lastname);

    @GetMapping(value = "/PatHistory/id/{patId}")
    PatientHistory getPatientByPatId(@PathVariable Long patId);

    @DeleteMapping(value = "/PatHistory/delete/{patId}")
    PatientHistory deletePatientById(@PathVariable Long patId);

    @PutMapping(value = "/PatHistory/update/{patId}")
    PatientHistory updatePatientById(@PathVariable Long patId, @RequestBody PatientHistory patientToUpdate);


}
