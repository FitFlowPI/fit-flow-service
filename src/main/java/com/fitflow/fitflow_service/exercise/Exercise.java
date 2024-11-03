package com.fitflow.fitflow_service.exercise;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String media;

    @Column(name = "current_weight")
    private BigDecimal currentWeight;

    @Column(name = "rest_interval")
    private int restInterval;
}