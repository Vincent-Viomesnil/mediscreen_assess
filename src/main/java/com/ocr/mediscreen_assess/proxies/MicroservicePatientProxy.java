package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen", url = "localhost:8081")
    public interface MicroservicePatientProxy {
        @GetMapping(value = "/Patients")
        List<Patient> patientList();

        @GetMapping(value = "/Patient/lastname/{lastname}")
        Optional<Patient> getPatientByLastname(@Valid @PathVariable("lastname") String lastname);

        @PostMapping(value = "/Patient/add")
        ResponseEntity<Object> addPatient(@Valid @RequestBody Patient patient);

        @PutMapping(value = "/Patient/update/{lastname}")
        Patient updatePatient(@PathVariable String lastname, @RequestBody Patient patientToUpdate);

        @DeleteMapping(value="/Patient/delete/{lastname}")
        Patient deletePatient(@PathVariable String lastname);

        @GetMapping(value = "Patient/id/{Id}")
        Optional<Patient> getPatientById(@PathVariable Long Id);
}
