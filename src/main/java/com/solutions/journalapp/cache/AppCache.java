package com.solutions.journalapp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.entity.ConfigJournalAppEntity;
import com.solutions.journalapp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class AppCache {
    private Map<String, String> appCache;

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct//use to auto call a method while a bean is created
    public void init(){
            appCache = new HashMap<>();
            List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
            for(ConfigJournalAppEntity configJournalAppEntity : all){
                appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
            }
           
    }
}
