package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateGenderRequest {
    @NotBlank(message = "Gender must not be blank")
    private String newGender;
}