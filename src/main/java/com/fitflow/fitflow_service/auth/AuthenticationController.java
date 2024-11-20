package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.auth.AuthenticationRequest;
import com.fitflow.fitflow_service.auth.AuthenticationResponse;
import com.fitflow.fitflow_service.auth.AuthenticationService;
import com.fitflow.fitflow_service.auth.RegisterRequest;
import com.fitflow.fitflow_service.config.ApiResponse;
import com.fitflow.fitflow_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request, authenticationManager));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.findByEmail(email).isPresent();
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authenticationService.register(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        SecurityContextHolder.clearContext();
        ApiResponse<Void> response = new ApiResponse<>(
                204,
                "Logout realizado com sucesso",
                null
        );
        return ResponseEntity.status(204).body(response);
    }
}
