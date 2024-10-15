package com.fitflow.fitflow_service.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(name = "/placeholder") //TODO trocar quando estiver documentado
public class AuthController {

    private AuthService authService;

}
