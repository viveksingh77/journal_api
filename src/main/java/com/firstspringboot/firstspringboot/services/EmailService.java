package com.firstspringboot.firstspringboot.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String to , String subject , String body){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("deepusingh6437@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
        }catch (Exception e){
            log.error("Exception occur while sendMail ",e);
        }
    }
}
