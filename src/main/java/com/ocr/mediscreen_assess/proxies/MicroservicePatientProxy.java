package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen", url = "localhost:8081")
public interface MicroservicePatientProxy {
    @GetMapping(value = "/Patients")
    List<PatientBean> patientList();

    @GetMapping(value = "/Patient/lastname/{lastname}")
    PatientBean getPatientByLastname(@PathVariable("lastname") String lastname);

    @GetMapping(value = "/Patient/id/{id}")
    PatientBean getPatientById(@PathVariable Long id);

    @PostMapping(value = "/Patient/add")
    PatientBean addPatient(@RequestBody PatientBean patient);

    @DeleteMapping(value = "/Patient/delete/{patId}")
    PatientBean deletePatient(@PathVariable Long patId);
}
