package com.ocr.mediscreen_assess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientHistory {
    @Id
    private ObjectId _id;

    private Long patId;
    private String lastname;
    private String notes;
}

