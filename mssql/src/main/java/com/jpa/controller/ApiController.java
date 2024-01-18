package com.jpa.controller;


import com.jpa.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {
    final ApiService apiService;

    @GetMapping(value = "/getData")
    public <T> T getData() {
        return apiService.getData();
    }

    @GetMapping(value = "/importLargeExcel")
    public void importLargeExcel() throws Exception {
        apiService.importExcel();
    }

}
