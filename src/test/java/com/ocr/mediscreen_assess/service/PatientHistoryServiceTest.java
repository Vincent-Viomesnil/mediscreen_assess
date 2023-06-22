package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.Patient;
import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.model.TriggerWords;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatientHistoryServiceTest {

    @Mock
    private TriggerWordsService triggerWordsService;

    @Mock
    private MicroserviceNotesProxy microserviceNotesProxy;

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;

    @InjectMocks
    private PatientHistoryService patientHistoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
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
        Date birthdate = new Date(90, 1, 1);
        String lastname = "Doe";
        Patient patient = new Patient();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setBirthdate(birthdate);
        patient.setGender("M");
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setNotes("Patient's notes");
        int expectedAge = 36;

        when(microservicePatientProxy.getPatientByLastname(lastname)).thenReturn(Optional.of(patient));
        when(microserviceNotesProxy.getPatientHistoryByLastname(lastname)).thenReturn(patientHistory);
        when(triggerWordsService.findAllTriggers()).thenReturn(createMockTriggerWords());
//        when(triggerWordsService.findAllTriggers().getTriggerList()).thenReturn(createMockTriggerList());

        // Act
        String result = patientHistoryService.getAssessmentByLastname(lastname);

        // Assert
        String expected = "John Doe (age 36) diabetes assessment is: None";
        assertEquals(expected, result);
        verify(microservicePatientProxy, times(1)).getPatientByLastname(lastname);
        verify(microserviceNotesProxy, times(1)).getPatientHistoryByLastname(lastname);
        verify(triggerWordsService, times(1)).findAllTriggers();
    }

    @Test
    public void testGetAssessmentById() {
        // Arrange
        Long patId = 1L;
        Date birthdate = new Date(90, 1, 1);
        Patient patient = new Patient();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setBirthdate(birthdate);
        patient.setGender("M");
        patient.setId(1L);
        patient.setAddress("address");
        patient.setPhonenumber("0123456789");
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.set_id(new ObjectId());
        patientHistory.setPatId(1L);
        patientHistory.setLastname("Doe");
        patientHistory.setNotes("Patient's notes");
        int expectedAge = 36;

        when(microservicePatientProxy.getPatientById(patId)).thenReturn(Optional.of(patient));
        when(microserviceNotesProxy.getPatientByPatId(patId)).thenReturn(patientHistory);
        when(triggerWordsService.findAllTriggers()).thenReturn(createMockTriggerWords());

        // Act
        String result = patientHistoryService.getAssessmentById(patId);

        // Assert
        String expected = "John Doe (age 36) diabetes assessment is: None";
        assertEquals(expected, result);
        verify(microservicePatientProxy, times(1)).getPatientById(patId);
        verify(microserviceNotesProxy, times(1)).getPatientByPatId(patId);
        verify(triggerWordsService, times(1)).findAllTriggers();
    }

    private TriggerWords createMockTriggerWords() {
        TriggerWords triggerWords = new TriggerWords();
        triggerWords.setTriggerList(List.of("Hémoglobine A1C", "Hemoglobin A1C",
                "Microalbumine", "Microalbumin", "Taille", "Height", "Poids", "Weight", "Smoker", "Fumeur", "Fume", "Smoke",
                "Abnormal", "Anormale", "Anormaux", "Cholesterol", "Cholestérol", "Vertige", "Dizziness",
                "Rechute", "Reaction", "Réaction", "Anticorps", "Antibodies"));
        return triggerWords;
    }
}
