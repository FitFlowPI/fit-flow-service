package com.fitflow.fitflow_service.trainingDay.dto;

import lombok.Data;

@Data
public class CreateTrainingDayExerciseRequest {
    private Long exerciseId;
    private Integer series;
    private Integer repetitions;
    private Double weight;
    private Integer restInterval;
}
