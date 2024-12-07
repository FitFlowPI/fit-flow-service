package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtService jwtService; // Inject JwtService to validate the token
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request, authenticationManager));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.findByEmail(email).isPresent();
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

    // Endpoint to handle password recovery request
    @PostMapping("/password-recovery")
    public ResponseEntity<String> recoverPassword(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendPasswordRecoveryEmail(emailRequest.getEmail());
            return ResponseEntity.ok("Password recovery email sent.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }

    // Endpoint to update password
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordUpdateRequest request) {
        try {
            // Fetch UserDetails using the email
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
    
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), new ArrayList<>()); // Assuming you're using email for the username
    
            // Validate the token using the UserDetails object
            boolean isValidToken = jwtService.isTokenValid(request.getToken(), userDetails);
            if (!isValidToken) {
                return ResponseEntity.status(400).body("Invalid or expired token.");
            }
    
            // Hash the new password with passwordEncoder.encode(newPassword) before saving
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(hashedPassword);
            userRepository.save(user);
    
            return ResponseEntity.ok("Password updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        }
    }

    // A DTO to represent the email in the request body
    public static class EmailRequest {
        @Email
        private String email;

        // Getter and Setter
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // A DTO to represent the password update request
    public static class PasswordUpdateRequest {
        private String token;
        @Email
        private String email;
        private String password;

        // Getter and Setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
