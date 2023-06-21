package com.ocr.mediscreen_assess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientBean {


    private Long id;

    private String firstname;

    private String lastname;

    private Date birthdate;

    private String gender;

    private String address;
    private String phonenumber;

}

