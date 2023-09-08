package com.example.statisticservice.service;

import com.example.statisticservice.dto.MessageDto;
import com.example.statisticservice.entity.Statistic;
import com.example.statisticservice.repo.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StatisticRepo statisticRepo;

//    @KafkaListener(id = "statisticGroup", topics = "statistic")
//    public void listen(Statistic statistic) {
////        logger.info("--------- Received: " + statistic.getMessage());
////        statisticRepo.save(statistic);
//        logger.info("111111111111 Received Statistic: " + statistic.getMessage());
//        throw new  RuntimeException();
//    }

//    @KafkaListener(id = "dltGroup", topics = "statistic.DLT")
//    public void dltListen(Statistic statistic){
//        logger.info("222222222222 Received Statistic DLT: " + statistic.getMessage());
//    }

    /*
     * attempts = "5": cố gắng thử lại 5 lần
     * dltTopicSuffix = "-dlt": lấy tên topic và kết hợp với từ "-dlt" thành tên mới
     * delay = 2_000: delay 2000ms, multiplier = 2 => tạo ra các topic retry (2000, 4000, 8000, 16000)
     * => sau mỗi lần false sẽ gửi vào retry và sau 5 lần vào vào DLT
     * */
    @RetryableTopic(attempts = "5", dltTopicSuffix = "-dlt",
            backoff = @Backoff(delay = 2000, multiplier = 2))
    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(MessageDto messageDto) {
//        logger.info("--------- Received: " + statistic.getMessage());
//        statisticRepo.save(statistic);
        logger.info(" Received Statistic: " + messageDto.getToName());
        throw new  RuntimeException();
    }
    @KafkaListener(id = "dltGroup", topics = "statistic-dlt")
    public void dltListen(MessageDto messageDto){
        logger.info(" Received Statistic DLT: " + messageDto.getToName());
    }
}

/**
 topic: notification có partition-1, partition-2
 - có 2 consumer chung 1 group (notificationGroup) thì mỗi consumer sẽ đọc 1 partition
 - có 2 consumer nhưng 2 group (notificationGroup1, notificationGroup2)
 thì cả 2 consumer đều đọc cả 2 partition

 khi đọc thì offset sẽ được lưu trong consumer (group nào lưu riêng group đó)
 * */
