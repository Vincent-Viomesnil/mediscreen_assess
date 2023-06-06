package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ocr.mediscreen_assess.repository.TriggerWordsDAO;

import java.util.List;

@Service
public class TriggerWordsService {

    @Autowired
    private TriggerWordsDAO triggerWordsDAO;

    public TriggerWords findAll() {
        return triggerWordsDAO.findAll();
    }
}
