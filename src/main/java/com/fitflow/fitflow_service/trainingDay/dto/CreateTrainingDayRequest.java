package com.fitflow.fitflow_service.trainingDay.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTrainingDayRequest {
    private String name;
    private List<CreateTrainingDayExerciseRequest> exercises;
}