package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateHeightRequest {
    @NotNull(message = "Height must not be null")
    private Float newHeight;
}