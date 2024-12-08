package com.fitflow.fitflow_service.exerciseExecution.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateExerciseExecutionRequest {
    private Double weightUsed;
    private Integer repetitions;
    private Integer restTime;
    private Integer seriesNumber;
    private Boolean completed;
    private Integer actualRestInterval;
    private String notes;
    private LocalDateTime executionTimestamp;
}
