package com.jpa.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ActivemqController {
    final JmsTemplate jmsTemplate;
    final Gson gson;

    final String PRINT_NAME = "PRINT_NAME";

    @PostMapping(value = "/sendMessage")
    public void sendMessage() {
        String message = "jms message!";
        jmsTemplate.convertAndSend(PRINT_NAME, message);

//        jmsTemplate.send(QueueName.PRINT_NAME, session -> session.createTextMessage(message));
//        jmsTemplate.send(QueueName.HANDLE_BATCH, session -> {
//            ObjectMessage objectMessage = session.createObjectMessage(message);
//            objectMessage.setObject(message);
//            return objectMessage;
//        });
    }

    @JmsListener(destination = PRINT_NAME)
    public void handleObj(String message) {
        try {
            log.info("received: {}", message);
//            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            log.error("\nhandleObj:\n", e);
        }
    }

}
