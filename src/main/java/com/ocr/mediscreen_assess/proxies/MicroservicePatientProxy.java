package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen", url = "localhost:8081")
public interface MicroservicePatientProxy {
    @GetMapping(value = "/Patients")
    List<PatientBean> patientList();

    @GetMapping(value = "/Patient/lastname/{lastname}")
    Optional<PatientBean> getPatientByLastname(@Valid @PathVariable("lastname") String lastname);

    @PostMapping(value = "/Patient/add")
    PatientBean addPatient(@Valid @RequestBody PatientBean patientBean);

    @PutMapping(value = "/Patient/update/{lastname}")
    PatientBean updatePatient(@PathVariable String lastname, @RequestBody PatientBean patientBeanToUpdate);

    @DeleteMapping(value = "/Patient/delete/{lastname}")
    PatientBean deletePatient(@PathVariable String lastname);

    @GetMapping(value = "Patient/id/{Id}")
    Optional<PatientBean> getPatientById(@PathVariable Long Id);
}
