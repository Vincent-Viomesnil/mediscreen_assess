package com.ocr.mediscreen_assess.repository;

import com.ocr.mediscreen_assess.model.TriggerWords;

public interface TriggerDao {
    TriggerWords findAllTriggers();
}
