package com.solutions.journalapp.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.solutions.journalapp.repository.UserRepository;

@SpringBootTest
public class UserServiceTests{
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void JournalPresentTest(){
        assertTrue(userRepository.findByUserName("Ani").getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
        "1,2,3",
        "4,6,10",
        "20,10,30"
    })
    @Disabled
    public void testAdd(int a,int b,int expected){
        assertEquals(expected, a+b);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Ani",
        "Homelander"
    })
    @Disabled
    public void userPresentTest(UserServiceTests userServiceTests, String name){
        assertNotNull(userServiceTests.userRepository.findByUserName(name),"Failed for :"+name);
    }
}
