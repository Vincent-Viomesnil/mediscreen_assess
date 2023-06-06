package com.ocr.mediscreen_assess.repository;


import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


    @Repository
    public class TriggerWordsDAO {
        public TriggerWords findAll() {
            TriggerWords triggerWords = new TriggerWords();
            return triggerWords;
        }


    }
