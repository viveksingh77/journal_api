package com.firstspringboot.firstspringboot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key ,Class<T> entity ) {
        try {
            Object object = redisTemplate.opsForValue().get(key);
            if (object!=null){
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(object.toString().trim(),entity);
            } else {
                return null;
            }

        }catch (Exception e){
            log.error("error to get data from reddis"+e);
            return null;
        }
    }

    public void set(String key , Object object , Long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(object);
           redisTemplate.opsForValue().set(key,jsonValue, TimeUnit.SECONDS.ordinal());
        }catch (Exception e){
            log.error("error"+e);
        }
    }
}
