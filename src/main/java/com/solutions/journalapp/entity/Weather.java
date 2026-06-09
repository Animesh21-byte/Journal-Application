package com.solutions.journalapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    
    public Current current;

    @Getter
    @Setter
    public static class Current {
        public int temperature;
    }

}
