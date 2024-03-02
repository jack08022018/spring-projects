package com.jpa.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisController {
    final RedisOperations<String, String> redisTemplateString;
    final Gson gson;

    @PostMapping(value = "/getData")
    public String getData(@RequestParam String key) throws InterruptedException {
        var data = redisTemplateString.opsForValue().get(key);
        if(data == null) {
            data = key + "_redis data!";
            redisTemplateString.opsForValue().set(key, data, Duration.ofSeconds(30));
            TimeUnit.SECONDS.sleep(3);
        }
        return data;
    }

}
