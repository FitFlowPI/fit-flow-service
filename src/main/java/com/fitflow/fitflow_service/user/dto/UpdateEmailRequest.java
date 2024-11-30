package com.fitflow.fitflow_service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmailRequest {
    @Email(message = "Invalid email")
    @NotBlank(message = "Email must not be blank")
    private String newEmail;
}