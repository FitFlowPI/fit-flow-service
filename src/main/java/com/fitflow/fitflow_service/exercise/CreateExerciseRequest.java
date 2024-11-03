package com.fitflow.fitflow_service.exercise;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateExerciseRequest {
    private String name;
    private String description;
    private String media;
    private BigDecimal currentWeight;
    private int restInterval;
}