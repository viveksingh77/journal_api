package com.firstspringboot.firstspringboot.service;

import com.firstspringboot.firstspringboot.repository.UserRepository;
import com.firstspringboot.firstspringboot.services.UserService;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testAdd() {

        assertEquals(4, 2 + 2);
        assertNotNull(userRepository.findByUserName("ram"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "vivek",
    })
    public void testFindByUserName(String name) {
        assertNotNull(userRepository.findByUserName(name));
    }




    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,3,5"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

}
