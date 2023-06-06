package com.ocr.mediscreen_assess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TriggerWords {
    private List<String> triggerList = new ArrayList<>(Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids", "Fumeur","Anormal",
 "Cholestérol","Vertige","Rechute","Réaction","Anticorps"));

}
