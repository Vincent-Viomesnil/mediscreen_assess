package com.ocr.mediscreen_assess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TriggerWords {
 List<String> triggerList = new ArrayList<>(Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids", "Fumeur","Anormal",
 "Cholestérol","Vertige","Rechute","Réaction","Anticorps"));

}
