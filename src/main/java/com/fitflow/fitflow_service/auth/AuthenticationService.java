package com.fitflow.fitflow_service.auth;

import com.fitflow.fitflow_service.common.exception.DataAlreadyExistsException;
import com.fitflow.fitflow_service.common.exception.ResourceNotFoundException;
import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.enums.UserType;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import com.fitflow.fitflow_service.user.service.UserWeightHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserWeightHistoryService userWeightHistoryService;

    public ApiResponse<AuthenticationResponse> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DataAlreadyExistsException("O e-mail já está registrado: " + request.getEmail());
        }

        UserType userType = request.getUser_type() != null ? request.getUser_type() : UserType.STUDENT;

        // Criação do usuário
        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active_plan(Boolean.TRUE.equals(request.getActive_plan()))
                .user_type(userType)
                .gender(request.getGender())
                .weight(request.getWeight())
                .height(request.getHeight())
                .build();

        userRepository.save(user);
        if (user.getWeight() != null) {
            userWeightHistoryService.saveUserWeightHistory(user.getId(), user.getWeight());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUser_type().toString());

        var jwtToken = jwtService.generateToken(claims, user);

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getName())
                .userType(userType.toString())
                .build();

        return ApiResponse.success("Usuário registrado com sucesso", authResponse, HttpStatus.CREATED.value());
    }

    public ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + request.getEmail()));

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUser_type().toString());

        var jwtToken = jwtService.generateToken(claims, user);

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .name(user.getName())
                .userType(user.getUser_type().toString())
                .build();

        return ApiResponse.success("Usuário autenticado com sucesso", authResponse, HttpStatus.CREATED.value());
    }
}