package com.ocr.mediscreen_assess.repository;


import com.ocr.mediscreen_assess.model.TriggerWords;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class TriggerWordsDAO  {
    public List<TriggerWords> findAll() {
        List<TriggerWords> triggerWordsList = new ArrayList<>();
        return triggerWordsList;
    }

    }


