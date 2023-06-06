package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ocr.mediscreen_assess.service.TriggerWordsService;

import java.util.List;

@RestController
public class TriggerWordsController {

    @Autowired
    private TriggerWordsService triggerWordsService;


    @RequestMapping(value = "/TriggerWordsList", method = RequestMethod.GET)
    public TriggerWords patientList() {
        return triggerWordsService.findAll();

    }
}
