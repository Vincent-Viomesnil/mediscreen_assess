package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.model.TriggerWords;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatientHistoryServiceTest {

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;

    @Mock
    private MicroserviceNotesProxy microserviceNotesProxy;

    @Mock
    private TriggerWordsService triggerWordsService;

    private PatientHistoryService patientHistoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        patientHistoryService = new PatientHistoryService(microserviceNotesProxy, microservicePatientProxy);
    }

    @Test
    public void testGetPatientHistoryList() {
        // Arrange
        List<PatientHistory> expectedPatientList = new ArrayList<>();
        when(microserviceNotesProxy.patientHistoryList()).thenReturn(expectedPatientList);

        // Act
        List<PatientHistory> result = patientHistoryService.getPatientHistoryList();

        // Assert
        assertEquals(expectedPatientList, result);
        verify(microserviceNotesProxy, times(1)).patientHistoryList();
    }

    @Test
    public void testGetAssessmentByLastname() {
        // Arrange

        String lastname = "Doe";
        Patient patient = new Patient();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        LocalDate specificDate = LocalDate.of(1987, 6, 21);
        Date date = Date.from(specificDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        patient.setBirthdate(date);
        patient.setPhonenumber("0123456789");
        patient.setAddress("Address");
        patient.setId(1L);
        patient.setGender("M");
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setLastname("Doe");
        patientHistory.set_id(new ObjectId());
        patientHistory.setPatId(1L);
        patientHistory.setNotes("Patient's notes");
        int expectedAge = 36;

        when(microservicePatientProxy.getPatientByLastname(lastname)).thenReturn(Optional.of(patient));
        when(microserviceNotesProxy.getPatientHistoryByLastname(lastname)).thenReturn(patientHistory);
        when(triggerWordsService.findAllTriggers()).thenReturn(createMockTriggerWords());


        // Act
        String result = patientHistoryService.getAssessmentByLastname("Doe");

        // Assert
        String expected = "John Doe (age 36) diabetes assessment is: None";
        assertEquals(expected, result);
    }


//    @Test
//    public void testGetAssessmentById() {
//        // Arrange
//        Long patId = 1L;
//        Patient patient = new Patient();
//        LocalDate specificDate = LocalDate.of(1987, 6, 21);
//        Date birthdate = Date.from(specificDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        patient.setFirstname("John");
//        patient.setLastname("Doe");
//        patient.setBirthdate(birthdate);
//        patient.setGender("M");
//        patient.setId(1L);
//        patient.setAddress("address");
//        patient.setPhonenumber("0123456789");
//        PatientHistory patientHistory = new PatientHistory();
//        patientHistory.set_id(new ObjectId());
//        patientHistory.setPatId(1L);
//        patientHistory.setLastname("Doe");
//        patientHistory.setNotes("Patient's notes");
//        int expectedAge = 36;
//
//        when(microservicePatientProxy.getPatientById(patId)).thenReturn(Optional.of(patient));
//        when(microserviceNotesProxy.getPatientByPatId(patId)).thenReturn(patientHistory);
//        when(triggerWordsService.findAllTriggers()).thenReturn(createMockTriggerWords());
//
//        // Act
//        String result = patientHistoryService.getAssessmentById(patId);
//
//        // Assert
//        String expected = "John Doe (age 36) diabetes assessment is: None";
//        assertEquals(expected, result);
//    }

    private TriggerWords createMockTriggerWords() {
        TriggerWords triggerWords = new TriggerWords();
        triggerWords.setTriggerList(List.of("Hémoglobine A1C", "Hemoglobin A1C",
                "Microalbumine", "Microalbumin", "Taille", "Height", "Poids", "Weight", "Smoker", "Fumeur", "Fume", "Smoke",
                "Abnormal", "Anormale", "Anormaux", "Cholesterol", "Cholestérol", "Vertige", "Dizziness",
                "Rechute", "Reaction", "Réaction", "Anticorps", "Antibodies"));
        return triggerWords;
    }
}
