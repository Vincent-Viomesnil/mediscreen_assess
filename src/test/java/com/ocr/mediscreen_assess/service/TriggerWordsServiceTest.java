package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.TriggerWords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriggerWordsServiceTest {

    private TriggerWordsService triggerWordsService;

    @BeforeEach
    public void setUp() {
        triggerWordsService = new TriggerWordsService();
    }

    @Test
    public void testFindAllTriggers_ReturnsTriggerList() {
        // Arrange
        List<String> expectedTriggerList = List.of("Hémoglobine A1C", "Hemoglobin A1C",
                "Microalbumine", "Microalbumin", "Taille", "Height", "Poids", "Weight", "Smoker", "Fumeur", "Fume", "Smoke",
                "Abnormal", "Anormale", "Anormaux", "Cholesterol", "Cholestérol", "Vertige", "Dizziness",
                "Rechute", "Reaction", "Réaction", "Anticorps", "Antibodies");

        // Act
        TriggerWords result = triggerWordsService.findAllTriggers();

        // Assert
        assertTrue(result.getTriggerList().containsAll(expectedTriggerList));
    }
}