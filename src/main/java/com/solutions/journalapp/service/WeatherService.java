package com.solutions.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.solutions.journalapp.cache.AppCache;
import com.solutions.journalapp.entity.Weather;

@Component
public class WeatherService {

    @Value("${WEATHER_STACK_API_KEY}")
    private String key;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    @Autowired
    RedisService redisService;

    public String getTemperature(String location) {
        Weather weather = redisService.get(location,Weather.class);
        if(weather != null){
            return String.valueOf(weather.getCurrent().getTemperature());
        }else{
            String finalApi = String.format(appCache.getAppCache().get("WEATHER_STACK_API"),key,location);
            ResponseEntity<Weather> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, Weather.class);
            redisService.set(location,response.getBody(),5);
            return String.valueOf(response.getBody().getCurrent().getTemperature());

        }
    }
}
