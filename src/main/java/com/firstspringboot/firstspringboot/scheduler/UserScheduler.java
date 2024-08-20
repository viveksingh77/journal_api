package com.firstspringboot.firstspringboot.scheduler;

import com.firstspringboot.firstspringboot.entity.JournalEntry;
import com.firstspringboot.firstspringboot.entity.Sentiment;
import com.firstspringboot.firstspringboot.entity.User;
import com.firstspringboot.firstspringboot.model.SentimentData;
import com.firstspringboot.firstspringboot.repository.UserRepositoryImpl;
import com.firstspringboot.firstspringboot.services.EmailService;
import com.firstspringboot.firstspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Scheduled(cron = "0/7 0 0 ? * * *")
    public void fetchUserAndSendMail(){
        List<User> users = userRepository.getUserForSA();
        for (User user :users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(ChronoLocalDate.from(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment , Integer> sentimentIntegerMap = new HashMap<>();
            for (Sentiment sentiment :sentiments){
                if (sentiment!=null){
                    sentimentIntegerMap.put(sentiment , sentimentIntegerMap.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment , Integer> entry : sentimentIntegerMap.entrySet()){
                if (entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment !=null){
                SentimentData sentiment = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment.toString()).build();
                try{
                    kafkaTemplate.send("weekly-sentiments" , sentiment.getEmail() , sentiment);
                }catch (Exception e){
                    emailService.sendMail(user.getEmail() , "Sentiment" ,"Sentiment for last 7 days " + mostFrequentSentiment.toString());
                }
            }


        }
    }
}
