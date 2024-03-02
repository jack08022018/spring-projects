package com.jpa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final ObjectMapper customObjectMapper;
    final Gson gson;

    public String localDateToString(LocalDateTime date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    public static String getErrorTraceMessage(Throwable e) {
        String errorMsg = Arrays.stream(e.getStackTrace())
                .filter(s -> s.getClassName().contains("com.jpa"))
                .map(s -> s.getClassName() + "." + s.getMethodName() + "(" + s.getFileName() + ":" + s.getLineNumber() + ")")
                .collect(Collectors.joining("; "));
        return String.format("message=%s cause=%s - %s", e.getMessage(), e.getCause(), errorMsg);
    }

}
