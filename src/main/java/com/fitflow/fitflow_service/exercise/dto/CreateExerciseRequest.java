package com.fitflow.fitflow_service.exercise.dto;

import lombok.Data;

@Data
public class CreateExerciseRequest {
    private String name;
    private String description;
    private String media;
    private Double currentWeight;
    private Integer restInterval;
}