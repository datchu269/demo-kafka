package com.example.statisticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication
public class StatisticServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticServiceApplication.class, args);
    }

    @Bean
    DefaultErrorHandler errorHandler(KafkaOperations<String, Object> template) {
        //DeadLetterPublishingRecoverer: gửi event lỗi đến dlt topic
        //FixedBackOff: sau 1s gửi lại event lỗi đó, gửi lại 2 lần
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

}
