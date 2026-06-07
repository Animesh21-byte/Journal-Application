package com.solutions.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.solutions.journalapp.entity.Quotes;
import org.springframework.beans.factory.annotation.Value;

@Component
public class QuoteService {
    @Value("${API_NINJAS_KEY}")
    private String key;
    private String api = "https://api.api-ninjas.com/v2/quoteoftheday";

    @Autowired
    RestTemplate restTemplate;

    public String getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", key);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Quotes[]> response = restTemplate.exchange(api, HttpMethod.GET, entity, Quotes[].class);
        return response.getBody()[0].getQuote();
    }
}
