package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNameRequest {
    @NotBlank(message = "Name must not be blank")
    private String newName;
}