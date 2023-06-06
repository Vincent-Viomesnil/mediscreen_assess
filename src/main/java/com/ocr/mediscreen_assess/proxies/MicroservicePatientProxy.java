package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

    @FeignClient(name = "mediscreen", url = "localhost:8081")
    public interface MicroservicePatientProxy {
        @GetMapping(value = "/Patients")
        List<Patient> patientList();

        @GetMapping(value = "/Patients/{lastname}")
        Patient getPatientByLastname(@PathVariable("lastname") String lastname);

    }
