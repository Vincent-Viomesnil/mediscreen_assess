package com.ocr.mediscreen_assess.repository;

import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class PatientDAO {

    public List<Patient> findAll() {
        List<Patient> patient = new ArrayList<>();
        return patient;
    }
}
