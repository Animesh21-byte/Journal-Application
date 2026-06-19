package com.solutions.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.solutions.journalapp.cache.AppCache;
import com.solutions.journalapp.entity.Quotes;
import org.springframework.beans.factory.annotation.Value;

@Component
public class QuoteService {
    @Value("${API_NINJAS_KEY}")
    private String key;
    

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppCache appCache;

    public String getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", key);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Quotes[]> response = restTemplate.exchange(appCache.getAppCache().get("API_NINJAS"), HttpMethod.GET, entity, Quotes[].class);
        return response.getBody()[0].getQuote();
    }
}
