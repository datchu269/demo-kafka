package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.dto.MessageDto;
import com.example.accountservice.dto.StatisticDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/new")
    public AccountDto create(@RequestBody AccountDto accountDto) {
        StatisticDto statisticDto = new StatisticDto("Account" + accountDto.getEmail() + "is created", new Date());

        // send notification
        MessageDto messageDto = new MessageDto();
        messageDto.setTo(accountDto.getEmail());
        messageDto.setToName(accountDto.getName());
        messageDto.setSubject("Welcome to Kafka");
        messageDto.setContent("Test");

        kafkaTemplate.send("notification", accountDto.getName(), messageDto);
        kafkaTemplate.send("statistic", accountDto.getName(), statisticDto);

        return accountDto;
    }
}
