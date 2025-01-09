package com.iiplabs.dltavro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Log4j2
@Configuration
public class PubSubConfig {

    @Bean
    public Consumer<Message<String>> receiveMessageFromTopicTwo() {
        return message -> {
            log.info(
                    "Message arrived via an input binder from topic-two! Payload: " + message.getPayload());
        };
    }

}
