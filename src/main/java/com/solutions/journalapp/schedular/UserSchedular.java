package com.solutions.journalapp.schedular;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.solutions.journalapp.cache.AppCache;
import com.solutions.journalapp.entity.JournalEntry;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.MongoRepositoryImpl;
import com.solutions.journalapp.service.EmailService;
import com.solutions.journalapp.service.SentimentAnalysisService;
public class UserSchedular {

    @Autowired
    private MongoRepositoryImpl mongoRepositoryImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 ? * SUN ")
    public void sentUsersSendSAMail(){
        List<User> users = mongoRepositoryImpl.findUserForSA();

        for(User user : users){
            List<JournalEntry> journalEntries = new ArrayList<>(user.getJournalEntries());
            List<String>filteredJournalEntry = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ",filteredJournalEntry);

            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "5 0 0 ? * * ")
    public void autoClearAppCache(){
        appCache.init();
    }
}
