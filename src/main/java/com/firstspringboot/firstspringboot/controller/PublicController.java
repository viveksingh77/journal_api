package com.firstspringboot.firstspringboot.controller;

import com.firstspringboot.firstspringboot.entity.User;
import com.firstspringboot.firstspringboot.entity.WeatherResponse;
import com.firstspringboot.firstspringboot.services.UserDetailsServiceImpl;
import com.firstspringboot.firstspringboot.services.UserService;
import com.firstspringboot.firstspringboot.services.WeatherService;
import com.firstspringboot.firstspringboot.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String token = jwtUtils.generateToken(user.getUserName());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occured while createAuthentication ",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response = weatherService.getWeather("kerala");
        String greeting = "";
        if (response != null) {
            greeting = "weather is like " + response.getCurrent().getTemperatureInC();

        }
        return new ResponseEntity<>("hi " + authentication.getName() + greeting, HttpStatus.OK);
    }


}
