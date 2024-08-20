package com.firstspringboot.firstspringboot.service;

import com.firstspringboot.firstspringboot.scheduler.UserScheduler;
import com.firstspringboot.firstspringboot.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserScheduler userScheduler;

    @Test
    void  testingScheduler(){
        userScheduler.fetchUserAndSendMail();
    }


    @Test
    void testSendMail(){
        emailService.sendMail("forkotlinuse@gmail.com" , "testing java mail","hello");
    }

}
