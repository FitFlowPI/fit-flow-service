package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateWeightRequest {
    @NotNull(message = "New weight must not be null")
    private Float newWeight;

    @NotBlank(message = "Email must not be blank")
    private String email;
}
