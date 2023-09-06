package com.example.statisticservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Data
//@NoArgsConstructor
//@SuperBuilder
//@AllArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    private String message;
    private Date createDate;

}
