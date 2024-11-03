package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.auth.AuthenticationRequest;
import com.fitflow.fitflow_service.auth.AuthenticationResponse;
import com.fitflow.fitflow_service.auth.RegisterRequest;
import com.fitflow.fitflow_service.config.ApiResponse;
import com.fitflow.fitflow_service.config.JwtService;
import com.fitflow.fitflow_service.user.User;
import com.fitflow.fitflow_service.user.UserRepository;
import com.fitflow.fitflow_service.user.UserWeightHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserWeightHistoryService userWeightHistoryService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ApiResponse<AuthenticationResponse> register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active_plan(request.getActive_plan() != null ? request.getActive_plan() : false)
                .user_type(request.getUser_type())
                .gender(request.getGender())
                .weight(request.getWeight())
                .height(request.getHeight())
                .build();
        userRepository.save(user);

        userWeightHistoryService.saveUserWeightHistory(user.getId(), user.getWeight());

        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getName())
                .userType(user.getUser_type().toString())
                .build();

        return new ApiResponse<>(HttpStatus.CREATED.value(), "Usuário registrado com sucesso", authResponse);
    }

    public ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getName())
                .userType(user.getUser_type().toString())
                .build();

        return new ApiResponse<>(HttpStatus.OK.value(), "Usuário autenticado com sucesso", authResponse);
    }
}
