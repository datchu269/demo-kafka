package com.example.accountservice.service;

import com.example.accountservice.dto.MessageDto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class PollingService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

//    @Scheduled(fixedDelay = 1000)
//    public void producer() {
//        List<MessageDto> messageDtos = repo.findByStatus(false);
//
//        Properties props = new Properties();
//        props.put("bootstrap.servers", bootstrapServers);
//        props.put("key.serializer", keySerializer);
//        props.put("value.serializer", valueSerializer);
//        Producer<String, Object> producer = new KafkaProducer<>(props);
//
//        for (MessageDto messageDto : messageDtos) {
////            kafkaTemplate.send("notification", messageDto.getToName(), messageDto, new Callback() {
////                @Override
////                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
////                    if (e != null) {
////                        // Xử lý lỗi khi gửi tin nhắn
////                    } else {
////                        // Xử lý khi tin nhắn gửi thành công
////                    }
////                }
////            });
//
//            ProducerRecord<String, Object> record = new ProducerRecord<>("notification", messageDto.getToName(), messageDto);
//
//            producer.send(record, (recordMetadata, e) -> {
//                if (e != null) {
//                    // Xử lý khi gửi lỗi
//                    logger.info("error");
//                } else {
//                    // Xử lý khi gửi thành công
//                    logger.info("success");
//                }
//            });
//
//        }
//
//        producer.close();
//    }

    public void producer2(MessageDto messageDto) {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", keySerializer);
        props.put("value.serializer", valueSerializer);
        Producer<String, Object> producer = new KafkaProducer<>(props);

        ProducerRecord<String, Object> recordNotification = new ProducerRecord<>("notification", messageDto.getToName(), messageDto);
        ProducerRecord<String, Object> recordStatistic = new ProducerRecord<>("statistic", messageDto.getToName(), messageDto);

        producer.send(recordNotification, (recordMetadata, e) -> {
            if (e != null) {
                // Xử lý khi gửi lỗi
                logger.info("----- error  notification------");
            } else {
                // Xử lý khi gửi thành công
                logger.info("------ success  notification------");
            }
        });

        producer.send(recordStatistic, (recordMetadata, e) -> {
            if (e != null) {
                // Xử lý khi gửi lỗi
                logger.info("----- error Statistic ------");
            } else {
                // Xử lý khi gửi thành công
                logger.info("------ success Statistic ------");
            }
        });

        producer.close();
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
    }
}
