package com.solutions.journalapp.schedular;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.cache.AppCache;
import com.solutions.journalapp.entity.JournalEntry;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.enums.Sentiment;
import com.solutions.journalapp.repository.MongoRepositoryImpl;
import com.solutions.journalapp.service.EmailService;

@Component
public class UserSchedular {

    @Autowired
    private MongoRepositoryImpl mongoRepositoryImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 ? * SUN ")
    public void sentUsersSendSAMail(){
        List<User> users = mongoRepositoryImpl.findUserForSA();

        for(User user : users){
            List<JournalEntry> journalEntries = new ArrayList<>(user.getJournalEntries());
            List<Sentiment>sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment maxSentiment = null;
            int maxCount = 0;
            for(Sentiment sentiment : sentimentCounts.keySet()){
                if(sentimentCounts.get(sentiment)>maxCount){
                    maxCount = sentimentCounts.get(sentiment);
                    maxSentiment = sentiment;
                }
            }
            if (maxSentiment == null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", "No sentiments deteced");    
            }else{
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", maxSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "5 0 0 ? * * ")
    public void autoClearAppCache(){
        appCache.init();
    }
}
