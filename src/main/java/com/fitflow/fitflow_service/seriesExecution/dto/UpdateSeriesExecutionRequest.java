package com.fitflow.fitflow_service.seriesExecution.dto;

import lombok.Data;

@Data
public class UpdateSeriesExecutionRequest {
    private Integer seriesNumber;
    private Integer actualRepetitions;
    private Double actualWeight;
    private Integer actualRestInterval;
    private Boolean completed;
    private String notes;
}