package com.firstspringboot.firstspringboot.services;

import com.firstspringboot.firstspringboot.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments" , groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }
    private void sendEmail(SentimentData sentimentData){
        emailService.sendMail(sentimentData.getEmail() , "Sentiment" , sentimentData.getSentiment());
    }
}
