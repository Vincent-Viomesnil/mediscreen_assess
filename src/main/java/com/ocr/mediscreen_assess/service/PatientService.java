package com.ocr.mediscreen_assess.service;


import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.repository.PatientDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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