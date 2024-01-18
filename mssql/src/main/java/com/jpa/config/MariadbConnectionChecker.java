package com.jpa.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@RequiredArgsConstructor
public class MariadbConnectionChecker implements ApplicationRunner {
    final EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            log.info("Connect to mariadb success!");
        }catch (Exception e) {
            log.info("Connect to mariadb failed!");
        }
    }
}