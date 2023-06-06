package com.ocr.mediscreen_assess.repository;

import com.ocr.mediscreen_assess.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientDAO extends JpaRepository<Patient, Long> {

    List<Patient> findAll();

}
