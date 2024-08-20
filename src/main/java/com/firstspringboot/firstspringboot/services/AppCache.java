package com.firstspringboot.firstspringboot.services;

import com.firstspringboot.firstspringboot.entity.ConfigJournal;
import com.firstspringboot.firstspringboot.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalRepository configJournalRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournal> all = configJournalRepository.findAll();
        for (ConfigJournal config : all) {
            APP_CACHE.put(config.getKey() , config.getValue());
        }
    }

}
