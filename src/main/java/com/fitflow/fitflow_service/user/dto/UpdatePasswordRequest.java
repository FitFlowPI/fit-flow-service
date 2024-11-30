package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotBlank(message = "Password must not be blank")
    private String newPassword;
}