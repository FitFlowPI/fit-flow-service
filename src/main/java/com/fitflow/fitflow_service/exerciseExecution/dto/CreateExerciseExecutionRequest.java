package com.fitflow.fitflow_service.exerciseExecution.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreateExerciseExecutionRequest {
    private Long exerciseId;
    private Double weightUsed;
    private Integer repetitions;
    private Integer restTime;
    private Integer seriesNumber;
    private Boolean completed;
    private Integer actualRestInterval;
    private String notes;
    private Date executionDate;
    private LocalDateTime executionTimestamp;
}
