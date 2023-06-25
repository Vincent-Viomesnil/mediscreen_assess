package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientHistoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "mediscreen-mdb", url = "localhost:8082")
public interface MicroserviceNotesProxy {
    @GetMapping(value = "/PatHistoryList")
    List<PatientHistoryBean> patientHistoryList();

    @GetMapping(value = "/PatHistory/lastname/{lastname}")
    PatientHistoryBean getPatientHistoryByLastname(@PathVariable("lastname") String lastname);

    @GetMapping(value = "/PatHistory/id/{patId}")
    PatientHistoryBean getPatientByPatId(@PathVariable Long patId);

}
