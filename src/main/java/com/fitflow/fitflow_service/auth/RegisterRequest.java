package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.user.enums.Gender;
import com.fitflow.fitflow_service.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Boolean active_plan;
    private UserType user_type;
    private Gender gender;
    private BigDecimal weight;
    private BigDecimal height;
}