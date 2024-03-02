package com.jpa.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisConnectionChecker implements ApplicationRunner {
    final RedisConnectionFactory redisConnectionFactory;

    @Override
    public void run(ApplicationArguments args) {
        try {
            redisConnectionFactory.getConnection().ping();
            log.info("Successfully connected to Redis server!");
        } catch (Exception e) {
            log.error("Failed to connect to Redis server. Error: " + e.getMessage());
        }
    }
}