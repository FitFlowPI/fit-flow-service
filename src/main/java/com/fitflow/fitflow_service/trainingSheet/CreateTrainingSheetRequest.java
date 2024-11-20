package com.fitflow.fitflow_service.trainingSheet;

import lombok.Data;

import java.util.List;

@Data
public class CreateTrainingSheetRequest {
    private String name;
    private Long creatorId;
    private List<Long> exerciseIds;
}