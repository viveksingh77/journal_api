package com.firstspringboot.firstspringboot.service;

import com.firstspringboot.firstspringboot.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void testSaveNumber(){
        Assertions.assertNotNull(userRepository.getUserForSA());
    }
}
