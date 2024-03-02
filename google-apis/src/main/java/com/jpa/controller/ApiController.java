package com.jpa.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    final ApiService apiService;
    final ObjectMapper customObjectMapper;
    final com.demo.AsyncDemoApplication asyncDemoApplication;

    @GetMapping(value = "/threadName")
    public String threadName() {
        return asyncDemoApplication.getPod();
//        return Thread.currentThread().toString();
    }

    @GetMapping(value = "/getDataAsync")
    public String getDataAsync() {
        return apiService.getDataAsync();
    }

}
