package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.PatientHistory;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import com.ocr.mediscreen_assess.service.PatientHistoryService;
import com.ocr.mediscreen_assess.service.TriggerWordsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PatientHistoryControllerTest {

    @Mock
    private MicroserviceNotesProxy microserviceNotesProxy;

    @Mock
    private MicroservicePatientProxy microservicePatientProxy;

    @Mock
    private TriggerWordsService triggerWordsService;

    @Mock
    private PatientHistoryService patientHistoryService;
    @InjectMocks
    private PatientHistoryController patientHistoryController;

    private List<PatientHistory> patientHistoryList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientHistoryList = new ArrayList<>();
        patientHistoryList.add(new PatientHistory(new ObjectId(), 1L, "lastname", "Notes 1"));
        patientHistoryList.add(new PatientHistory(new ObjectId(), 2L, "emantsal", "Notes 2"));
    }

//    @Test
//    public void testPatientHistoryList() {
//        // Arrange
//        when(patientHistoryService.getPatientHistoryList()).thenReturn(patientHistoryList);
//
//        // Act
//        List<PatientHistory> result = patientHistoryController.patientHistoryList();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(patientHistoryList, result);
//        verify(patientHistoryService, times(1)).getPatientHistoryList();
//    }

//    @Test
//    public void testGetAssessmentByLastname() {
//        // Arrange
//        String lastname = "oklastname";
//        String assessment = "Notes";
//        when(patientHistoryService.getAssessmentByLastname(lastname)).thenReturn(assessment);
//
//        // Act
//        String result = patientHistoryController.getAssessmentByLastname(lastname);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(assessment, result);
//        verify(patientHistoryService, times(1)).getAssessmentByLastname(lastname);
//    }
//
//    @Test
//    public void testGetAssessmentById() {
//        // Arrange
//        Long patId = 1L;
//        String assessment = "Notes 1";
//        when(patientHistoryService.getAssessmentById(patId)).thenReturn(assessment);
//
//        // Act
//        String result = patientHistoryController.getAssessmentById(patId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(assessment, result);
//        verify(patientHistoryService, times(1)).getAssessmentById(patId);
//    }

    @Test
    public void testAddPatientHistory() {
        // Arrange
        PatientHistory patientHistory = new PatientHistory(new ObjectId(), 15L, "autrenom", "autres notes");
        when(microserviceNotesProxy.addPatientHistory(patientHistory)).thenReturn(patientHistory);

        // Act
        PatientHistory result = patientHistoryController.addPatientHistory(patientHistory);

        // Assert
        assertNotNull(result);
        assertEquals(patientHistory, result);
        verify(microserviceNotesProxy, times(1)).addPatientHistory(patientHistory);
    }

    @Test
    public void testUpdatePatientByLastname() {
        // Arrange
        String lastname = "lastname";
        PatientHistory patientToUpdate = new PatientHistory(new ObjectId(), 15L, "autrenom", "autres notes");
        PatientHistory updatedPatientHistory = new PatientHistory(new ObjectId(), 1L, "John", "Updated Assessment");
        when(microserviceNotesProxy.updatePatientByLastname(lastname, patientToUpdate)).thenReturn(updatedPatientHistory);

        // Act
        PatientHistory result = patientHistoryController.updatePatientByLastname(lastname, patientToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPatientHistory, result);
        verify(microserviceNotesProxy, times(1)).updatePatientByLastname(lastname, patientToUpdate);
    }

    @Test
    public void testUpdatePatientById() {
        // Arrange
        Long patId = 1L;
        PatientHistory patientToUpdate = new PatientHistory(new ObjectId(), 1L, "autrenom", "autres notes");
        PatientHistory updatedPatientHistory = new PatientHistory(new ObjectId(), 15L, "autrenom", "Notes updates");
        when(microserviceNotesProxy.updatePatientById(patId, patientToUpdate)).thenReturn(updatedPatientHistory);

        // Act
        PatientHistory result = patientHistoryController.updatePatientById(patId, patientToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPatientHistory, result);
        verify(microserviceNotesProxy, times(1)).updatePatientById(patId, patientToUpdate);
    }

    @Test
    public void testDeletePatientByLastname() {
        // Arrange
        String lastname = "autrenom";
        PatientHistory deletedPatientHistory = new PatientHistory(new ObjectId(), 15L, "autrenom", "autres notes");
        when(microserviceNotesProxy.deletePatientByLastname(lastname)).thenReturn(deletedPatientHistory);

        // Act
        PatientHistory result = patientHistoryController.deletePatientByLastname(lastname);

        // Assert
        assertNotNull(result);
        assertEquals(deletedPatientHistory, result);
        verify(microserviceNotesProxy, times(1)).deletePatientByLastname(lastname);
    }

    @Test
    public void testDeletePatientById() {
        // Arrange
        Long patId = 1L;
        PatientHistory deletedPatientHistory = new PatientHistory(new ObjectId(), 1L, "autrenom", "autres notes");
        when(microserviceNotesProxy.deletePatientById(patId)).thenReturn(deletedPatientHistory);

        // Act
        PatientHistory result = patientHistoryController.deletePatientById(patId);

        // Assert
        assertNotNull(result);
        assertEquals(deletedPatientHistory, result);
        verify(microserviceNotesProxy, times(1)).deletePatientById(patId);
    }

//    @Test
//    public void testGetAssessmentByPatId() {
//        // Arrange
//        Long patId = 1L;
//        PatientHistory patientHistory = new PatientHistory(new ObjectId(), 1L, "autrenom", "autres notes");
//        when(microserviceNotesProxy.getPatientByPatId(patId)).thenReturn(patientHistory);
//
//        // Act
//        PatientHistory result = patientHistoryController.getAssessmentByPatId(patId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(patientHistory, result);
//        verify(microserviceNotesProxy, times(1)).getPatientByPatId(patId);

//    @Test
//    public void testGetAssessmentByLastname() {
//        PatientHistory patientHistory = new PatientHistory(new ObjectId(), 15L, "autrenom", "autres notes");
//        String expectedAssessment = "Some assessment";
//        when(patientHistoryService.getAssessmentByLastname(patientHistory.getLastname())).thenReturn(expectedAssessment);
//
//        String result = patientHistoryController.getAssessmentByLastname(patientHistory.getLastname());
//
//        assertEquals(expectedAssessment, result);
//    }

}











