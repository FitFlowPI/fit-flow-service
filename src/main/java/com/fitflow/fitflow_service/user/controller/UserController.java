package com.fitflow.fitflow_service.user.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.dto.UpdateWeightRequest;
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

    @PutMapping("/weight")
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


