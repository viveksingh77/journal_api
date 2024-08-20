package com.firstspringboot.firstspringboot.controller;

import com.firstspringboot.firstspringboot.entity.JournalEntry;
import com.firstspringboot.firstspringboot.entity.User;
import com.firstspringboot.firstspringboot.repository.UserRepository;
import com.firstspringboot.firstspringboot.services.JournalEntryService;
import com.firstspringboot.firstspringboot.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @PostMapping
//    public void createUser(@RequestBody User user) {
//        userService.saveNewUser(user);
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        //securityholdercontext hold the username adn password of that user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        //securityholdercontext hold the username adn password of that user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
