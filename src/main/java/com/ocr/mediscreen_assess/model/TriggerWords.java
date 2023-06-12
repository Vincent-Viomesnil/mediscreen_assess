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
 private List<String> triggerList = new ArrayList<>(Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids", "Fumeur","Anormal",
 "Cholestérol","Vertige","Rechute","Réaction","Anticorps"));

 public List<String> getTriggerList() {
  return triggerList;
 }
}
