package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWeightRequest {
    @NotNull(message = "New weight must not be null")
    private Float newWeight;
}