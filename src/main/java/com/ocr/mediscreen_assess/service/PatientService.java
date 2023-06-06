package com.ocr.mediscreen_assess.service;


import lombok.extern.slf4j.Slf4j;
import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ocr.mediscreen_assess.repository.PatientDAO;

import java.util.List;


@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientDAO patientDAO;

    public List<Patient> findAll() {
        return patientDAO.findAll();
    }

}