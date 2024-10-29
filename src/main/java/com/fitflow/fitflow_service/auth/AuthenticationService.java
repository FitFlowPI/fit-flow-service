package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.config.JwtService;
import com.fitflow.fitflow_service.user.UserRepository;
import com.fitflow.fitflow_service.user.User;
import com.fitflow.fitflow_service.user.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active_plan(request.getActive_plan() != null ? request.getActive_plan() : false) // Ensure active_plan is set
                .user_type(request.getUser_type())
                .gender(request.getGender())
                .weight(request.getWeight())
                .height(request.getHeight())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse()
                .builder()
                .token(jwtToken)
                .build();
    }
}
