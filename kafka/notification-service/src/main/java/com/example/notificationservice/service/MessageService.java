package com.example.notificationservice.service;

import com.example.notificationservice.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    // id: để khi chạy 2 con khác nhau nếu có id thì nó sẽ chung group
    // topics: đọc topic có tên notification
    public void listen(MessageDto messageDto) {
        logger.info("Received notificationGroup: " + messageDto.getToName());
//        emailService.sendEmail(messageDto);
    }

}
