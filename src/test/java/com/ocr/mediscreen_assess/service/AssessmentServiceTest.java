package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.PatientBean;
import com.ocr.mediscreen_assess.model.PatientHistoryBean;
import com.ocr.mediscreen_assess.proxies.MicroserviceNotesProxy;
import com.ocr.mediscreen_assess.proxies.MicroservicePatientProxy;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssessmentServiceTest {

    @MockBean
    private MicroserviceNotesProxy microserviceNotesProxy;

    @MockBean
    private MicroservicePatientProxy microservicePatientProxy;

    @InjectMocks
    private AssessmentService assessmentService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAssessmentById_None() {
        // Arrange
        Long patId = 1L;
        PatientBean patient = new PatientBean();
        patient.setFirstname("First");
        patient.setLastname("Last");
        patient.setBirthdate(LocalDate.of(1980, 1, 1));
        patient.setGender("M");

        List<PatientHistoryBean> patientHistoryList = new ArrayList<>();
        PatientHistoryBean history1 = new PatientHistoryBean();
        history1.setNotes("Some notes");
        patientHistoryList.add(history1);

        when(microservicePatientProxy.getPatientById(patId)).thenReturn(patient);
        when(microserviceNotesProxy.getListNotesByPatId(patId)).thenReturn(patientHistoryList);

        // Act
        String result = assessmentService.getAssessmentById(patId);

        // Assert
        assertEquals("First Last (age 43) diabetes assessment is: None", result);
        verify(microservicePatientProxy, times(1)).getPatientById(patId);
        verify(microserviceNotesProxy, times(1)).getListNotesByPatId(patId);
    }

    @Test
    public void testGetAssessmentById_Borderline() {
        // Arrange
        Long patId = 1L;
        PatientBean patient = new PatientBean();
        patient.setId(patId);
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setBirthdate(LocalDate.of(1970, 1, 1));
        patient.setGender("M");
        patient.setAddress("address");
        patient.setPhonenumber("0123456789");

        List<PatientHistoryBean> patientHistoryList = new ArrayList<>();
        PatientHistoryBean history1 = new PatientHistoryBean();
        history1.set_id(new ObjectId());
        history1.setNoteId(1L);
        history1.setPatId(1L);
        history1.setLastname("Doe");
        history1.setNotes("Height");

        PatientHistoryBean history2 = new PatientHistoryBean();
        history2.set_id(new ObjectId());
        history2.setNoteId(2L);
        history2.setPatId(1L);
        history2.setLastname("Doe");
        history2.setNotes("Fumeur");

        patientHistoryList.add(history1);
        patientHistoryList.add(history2);


        when(microservicePatientProxy.getPatientById(patId)).thenReturn(patient);
        when(microserviceNotesProxy.getListNotesByPatId(patId)).thenReturn(patientHistoryList);

        // Act
        String result = assessmentService.getAssessmentById(patId);

        // Assert
        assertEquals("John Doe (age 53) diabetes assessment is: Borderline", result);
        verify(microservicePatientProxy, times(1)).getPatientById(patId);
        verify(microserviceNotesProxy, times(1)).getListNotesByPatId(patId);
    }

    @Test
    public void testGetAssessmentById_Danger() {
        // Arrange
        Long patId = 1L;
        PatientBean patient = new PatientBean();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setBirthdate(LocalDate.of(1995, 1, 1));
        patient.setGender("M");

        List<PatientHistoryBean> patientHistoryList = new ArrayList<>();
        PatientHistoryBean history1 = new PatientHistoryBean();
        history1.setNotes("Trigger 1");
        history1.setNotes("Trigger 2");
        history1.setNotes("Trigger 3");
        patientHistoryList.add(history1);

        when(microservicePatientProxy.getPatientById(patId)).thenReturn(patient);
        when(microserviceNotesProxy.getListNotesByPatId(patId)).thenReturn(patientHistoryList);

        // Act
        String result = assessmentService.getAssessmentById(patId);

        // Assert
        assertEquals("John Doe (age 28) diabetes assessment is: In Danger", result);
        verify(microservicePatientProxy, times(1)).getPatientById(patId);
        verify(microserviceNotesProxy, times(1)).getListNotesByPatId(patId);
    }

    @Test
    public void testGetAssessmentById_EarlyOnSet() {
        // Arrange
        Long patId = 1L;
        PatientBean patient = new PatientBean();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setBirthdate(LocalDate.of(1985, 1, 1));
        patient.setGender("F");

        List<PatientHistoryBean> patientHistoryList = new ArrayList<>();
        PatientHistoryBean history1 = new PatientHistoryBean();
        history1.setNotes("Trigger 1");
        history1.setNotes("Trigger 2");
        history1.setNotes("Trigger 3");
        history1.setNotes("Trigger 4");
        history1.setNotes("Trigger 5");
        patientHistoryList.add(history1);

        when(microservicePatientProxy.getPatientById(patId)).thenReturn(patient);
        when(microserviceNotesProxy.getListNotesByPatId(patId)).thenReturn(patientHistoryList);

        // Act
        String result = assessmentService.getAssessmentById(patId);

        // Assert
        assertEquals("John Doe (age 38) diabetes assessment is: Early onset", result);
        verify(microservicePatientProxy, times(1)).getPatientById(patId);
        verify(microserviceNotesProxy, times(1)).getListNotesByPatId(patId);
    }
}

