package com.fitflow.fitflow_service.trainingProgram.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateTrainingProgramRequest {
    private String name;
    private String description;
    private List<Long> trainingDayIds;
}