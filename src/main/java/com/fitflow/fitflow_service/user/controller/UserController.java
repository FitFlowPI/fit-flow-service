package com.fitflow.fitflow_service.user.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.dto.*;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PutMapping("/update-name")
    @PreAuthorize("hasAnyRole('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updateName(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateNameRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updateName(user, request.getNewName());

        ApiResponse<String> response = new ApiResponse<>(true, "Name updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-email")
    @PreAuthorize("hasAnyRole('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updateEmail(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateEmailRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updateEmail(user, request.getNewEmail());

        ApiResponse<String> response = new ApiResponse<>(true, "Email updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updatePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdatePasswordRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updatePassword(user, request.getNewPassword());

        ApiResponse<String> response = new ApiResponse<>(true, "Password updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-gender")
    @PreAuthorize("hasAnyRole('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updateGender(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateGenderRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updateGender(user, request.getNewGender());

        ApiResponse<String> response = new ApiResponse<>(true, "Gender updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-height")
    @PreAuthorize("hasAnyRole('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updateHeight(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateHeightRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updateHeight(user, request.getNewHeight());

        ApiResponse<String> response = new ApiResponse<>(true, "Height updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-weight")
    @PreAuthorize("hasRole('STUDENT') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<?>> updateWeight(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateWeightRequest request
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        userService.updateWeight(user, request.getNewWeight());

        ApiResponse<String> response = new ApiResponse<>(true, "Weight updated successfully", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyRole('STUDENT', 'PERSONAL_TRAINER', 'AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<User>> getUserDetails(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        ApiResponse<User> response = new ApiResponse<>(true, "User details retrieved successfully", user, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}