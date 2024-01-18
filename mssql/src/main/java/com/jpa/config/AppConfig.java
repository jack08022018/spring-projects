package com.jpa.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jpa.utils.LocalDateTimeISOTypeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Configuration
public class AppConfig {

    @Bean(name = "customObjectMapper")
    public ObjectMapper getObjectMapper() {
        var mapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeISOTypeAdapter())
//                .setPrettyPrinting()
                .create();
    }

    @Bean
    public Gson gsonIgnoreField() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeISOTypeAdapter())
                .setExclusionStrategies(new CustomExclusionStrategy())
                .create();
    }

    static class CustomExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            var fields = Arrays.asList("memoizedIsInitialized", "unknownFields", "memoizedSize", "memoizedHashCode");
            return fields.contains(f.getName());
//            return f.getName().equals("memoizedIsInitialized");
        }
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false; // Return false to include all classes
        }
    }

}