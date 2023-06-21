package com.ocr.mediscreen_assess.proxies;

import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
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
    Patient addPatient(@Valid @RequestBody Patient patient);

    @RequestMapping(value = "Patient/update", method = RequestMethod.PUT)
    Patient updatePatientByLastname(@RequestParam("lastname") String lastname, @RequestBody Patient patientToUpdate);

    @PutMapping(value = "/Patient/update/{id}")
    Patient updatePatientById(@PathVariable Long id, @RequestBody Patient patientToUpdate);

    @RequestMapping(value = "Patient/delete", method = RequestMethod.DELETE)
    Patient deletePatientByLastname(@RequestParam("lastname") String lastname);

    @DeleteMapping(value = "/Patient/delete/{id}")
    Patient deletePatientById(@PathVariable Long id);

    @GetMapping(value = "Patient/id/{id}")
    Optional<Patient> getPatientById(@PathVariable Long id);
}
