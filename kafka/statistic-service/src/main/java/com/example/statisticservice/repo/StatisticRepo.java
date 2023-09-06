package com.example.statisticservice.repo;

import com.example.statisticservice.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepo extends JpaRepository<Statistic, Integer> {
}
