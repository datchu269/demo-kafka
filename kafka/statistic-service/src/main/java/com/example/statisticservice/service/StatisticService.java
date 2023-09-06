package com.example.statisticservice.service;

import com.example.statisticservice.entity.Statistic;
import com.example.statisticservice.repo.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StatisticRepo statisticRepo;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
//        statisticRepo.save(statistic);
    }

    @KafkaListener(id = "dltGroup", topics = "statistic.DLT")
    public void dltListen(Statistic statistic){
        logger.info("Received Statistic DLT: " + statistic.getMessage());
    }
}
