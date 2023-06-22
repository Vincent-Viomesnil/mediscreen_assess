package com.ocr.mediscreen_assess.service;

import com.ocr.mediscreen_assess.model.TriggerWords;
import com.ocr.mediscreen_assess.repository.TriggerDao;
import org.springframework.stereotype.Service;

@Service
public class TriggerWordsService implements TriggerDao {
    private TriggerWords triggerList = new TriggerWords(); // Liste de mots déclencheurs

    @Override
    public TriggerWords findAllTriggers() {
        return triggerList;
    }

}
