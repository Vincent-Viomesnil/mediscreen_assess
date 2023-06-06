package com.ocr.mediscreen_assess.repository;


import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
    public class TriggerWordsDAO {
        public TriggerWords findAll() {
            TriggerWords triggerWords = new TriggerWords();
            return triggerWords;
        }

//        public Optional<TriggerWords> findByWord(String word) {
//            TriggerWords triggerWords = new TriggerWords();
//            triggerWords.getTriggerList().stream().filter(s -> s.contains(word));
//            return Optional.of(triggerWords);
//        }
    }
