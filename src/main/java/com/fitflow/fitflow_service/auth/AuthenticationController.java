package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.auth.AuthenticationRequest;
import com.fitflow.fitflow_service.auth.AuthenticationResponse;
import com.fitflow.fitflow_service.auth.AuthenticationService;
import com.fitflow.fitflow_service.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("Registering user: " + request.getUsername());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request, authenticationManager));
    }
}
