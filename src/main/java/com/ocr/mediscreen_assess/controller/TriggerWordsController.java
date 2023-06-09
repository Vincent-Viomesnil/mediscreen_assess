package com.ocr.mediscreen_assess.controller;

import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ocr.mediscreen_assess.service.TriggerWordsService;


import java.util.List;

@RestController
public class TriggerWordsController {

    @Autowired
    private TriggerWordsService triggerWordsService;

    @RequestMapping(value = "/TriggerWordsList", method = RequestMethod.GET)
    public List<TriggerWords> patientList() {
        return triggerWordsService.findAll();

    }

//    @RequestMapping(value = "/TriggerWordsList/{word}", method = RequestMethod.GET)
//    public Optional<TriggerWords> getTriggerWords(@PathVariable String word) {
//        Optional<TriggerWords> triggerWords = triggerWordsService.findByWord(word);
//        if (triggerWords.isEmpty()) {
//         System.out.println("word: " + word + " is not found");
//        }
//        System.out.println("word: " + word + " is found");
//        return triggerWords;
//    }
        }