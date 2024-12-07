package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.user.enums.Gender;
import com.fitflow.fitflow_service.user.enums.UserType;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "O nome do usuário é obrigatório")
    private String username;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String password;

    private Boolean active_plan = true; //TODO: se o usuário começar como false ele não consegue fazer login, precisaremos alterar para que ele consiga fazer login mas não tenha acesso as funcionalidades pagas

    private UserType user_type;

    private Gender gender;

    private Float weight;

    private Float height;
}