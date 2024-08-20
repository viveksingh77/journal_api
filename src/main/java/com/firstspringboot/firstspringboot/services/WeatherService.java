package com.firstspringboot.firstspringboot.services;

import com.firstspringboot.firstspringboot.entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        //check reddis if data exits then return
        WeatherResponse response = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (response!=null){
            return response;
        }else {
            String finalapi = appCache.APP_CACHE.get("weather_api").replace("<city>",city).replace("<api_key>",apikey);
            ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalapi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = exchange.getBody();

            //saving in  reddis if data exits
            if (body!=null){
                redisService.set("weather_of_"+city,body , 300L);
            }
            return body;
        }

    }
}
