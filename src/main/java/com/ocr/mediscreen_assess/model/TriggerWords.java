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
 private List<String> triggerList = new ArrayList<>(Arrays.asList("Hémoglobine A1C","Hemoglobin A1C", "Microalbumine","Taille","Poids","Smoker", "Fumeur","Abnormal",
 "Cholesterol","Vertige","Dizziness","Rechute","Reaction","Anticorps","Antibodies", "réaction"));

 public List<String> getTriggerList() {
  return triggerList;
 }
}
