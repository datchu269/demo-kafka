package com.example.statisticservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {
    // convert byte stream to object
    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }
}
