package controller;

import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.PatientService;

import java.util.List;


@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    //Retrieve patients'list
    @RequestMapping(value = "/Patients", method = RequestMethod.GET)
    public List<Patient> patientList() {
        List<Patient> patientList = patientService.findAll();
        return patientList;
    }

}
