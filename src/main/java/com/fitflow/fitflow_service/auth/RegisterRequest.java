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
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    private Boolean active_plan;

    @NotNull(message = "O tipo de usuário é obrigatório")
    private UserType user_type;

    @NotNull(message = "O gênero é obrigatório")
    private Gender gender;

    @NotNull(message = "O peso é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O peso deve ser maior que 0")
    private Float weight;

    @NotNull(message = "A altura é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "A altura deve ser maior que 0")
    private Float height;
}
