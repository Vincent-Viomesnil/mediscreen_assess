package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;

    @InjectMocks
    private PatientController patientController;

    private List<Patient> patientList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientList = new ArrayList<>();
        patientList.add(new Patient(1L, "firstname", "firstname", new Date(), "F", "address", "0102030405"));
        patientList.add(new Patient(2L, "lastname", "lastname", new Date(), "F", "address", "1234567890"));
    }

    @Test
    public void testGetPatientList() {
        // Arrange
        when(microservicePatientProxy.patientList()).thenReturn(patientList);

        // Act
        List<Patient> result = patientController.getPatientList();

        // Assert
        assertNotNull(result);
        assertEquals(patientList, result);
        verify(microservicePatientProxy, times(1)).patientList();
    }

    @Test
    public void testGetPatientByLastname() {
        // Arrange
        String lastname = "last";
        Optional<Patient> patient = Optional.of(new Patient(1L, "firstname", "last", new Date(), "F", "address", "0102030405"));
        when(microservicePatientProxy.getPatientByLastname(lastname)).thenReturn(patient);

        // Act
        Optional<Patient> result = patientController.getPatientByLastname(lastname);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result);
        verify(microservicePatientProxy, times(1)).getPatientByLastname(lastname);
    }

    @Test
    public void testGetPatientByLastnameInvalid() {
        // Arrange
        String lastname = "Invalid";
        Optional<Patient> patient = Optional.empty();
        when(microservicePatientProxy.getPatientByLastname(lastname)).thenReturn(patient);

        // Act
        Optional<Patient> result = patientController.getPatientByLastname(lastname);

        // Assert
        assertNotNull(result);
        assertFalse(result.isPresent());
        verify(microservicePatientProxy, times(1)).getPatientByLastname(lastname);
    }

    @Test
    public void testGetPatientByIdt() {
        // Arrange
        Long id = 1L;
        Optional<Patient> patient = Optional.of(new Patient(1L, "firstname", "last", new Date(), "F", "address", "0102030405"));
        when(microservicePatientProxy.getPatientById(id)).thenReturn(patient);

        // Act
        Optional<Patient> result = patientController.getPatientById(id);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result);
        verify(microservicePatientProxy, times(1)).getPatientById(id);
    }

    @Test
    public void testGetPatientByIdInvalidId() {
        // Arrange
        Long id = 10L;
        Optional<Patient> patient = Optional.empty();
        when(microservicePatientProxy.getPatientById(id)).thenReturn(patient);

        // Act
        Optional<Patient> result = patientController.getPatientById(id);

        // Assert
        assertNotNull(result);
        assertFalse(result.isPresent());
        verify(microservicePatientProxy, times(1)).getPatientById(id);
    }

    @Test
    public void testAddPatientValidPatient() {
        // Arrange
        Patient patient = new Patient(1L, "firstname", "last", new Date(), "F", "address", "0102030405");
        when(microservicePatientProxy.addPatient(patient)).thenReturn(patient);

        // Act
        Patient result = patientController.addPatient(patient);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result);
        verify(microservicePatientProxy, times(1)).addPatient(patient);
    }

    @Test
    public void testUpdatePatientByLastname() {
        // Arrange
        String lastname = "firstname";
        Patient patientToUpdate = new Patient(1L, "firstname", "firstname", new Date(), "F", "address", "0102030405");
        Patient updatedPatient = new Patient(1L, "updated", "updated", new Date(), "F", "address", "0102030405");
        when(microservicePatientProxy.updatePatientByLastname(lastname, patientToUpdate)).thenReturn(updatedPatient);

        // Act
        Patient result = patientController.updatePatientByLastname(lastname, patientToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPatient, result);
        verify(microservicePatientProxy, times(1)).updatePatientByLastname(lastname, patientToUpdate);
    }

    @Test
    public void testDeletePatientByLastname() {
        // Arrange
        String lastname = "lastname";
        Patient deletedPatient = new Patient(1L, "firstname", "firstname", new Date(), "F", "address", "0102030405");
        when(microservicePatientProxy.deletePatientByLastname(lastname)).thenReturn(deletedPatient);

        // Act
        Patient result = patientController.deletePatientByLastname(lastname);

        // Assert
        assertNotNull(result);
        assertEquals(deletedPatient, result);
        verify(microservicePatientProxy, times(1)).deletePatientByLastname(lastname);
    }
}






