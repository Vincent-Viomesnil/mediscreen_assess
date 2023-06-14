package com.ocr.mediscreen_assess.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TriggerWords {
 private List<String> triggerList = new ArrayList<>(Arrays.asList("Hémoglobine A1C","Hemoglobin A1C","hémoglobine A1C",
         "Microalbumine","microalbumine","Taille","Height","Poids","Weight","Smoker", "Fumeur","fume",
         "Abnormal","anormale","anormaux","Cholesterol","cholestérol","Vertige","vertige","Dizziness",
         "Rechute","Reaction","réaction","Anticorps","Antibodies"));
//Adding word in french in order to adapt the behaviour
 public List<String> getTriggerList() {
  return triggerList;
 }
}
