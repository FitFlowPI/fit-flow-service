package com.fitflow.fitflow_service.trainingDay.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayExerciseRequest;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayRequest;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import com.fitflow.fitflow_service.trainingDay.service.TrainingDayService;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training-day")
public class TrainingDayController {

    private final TrainingDayService trainingDayService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> createTrainingDay(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTrainingDayRequest request) {
        User creator = userService.findUserByEmail(userDetails.getUsername());
        TrainingDay trainingDay = trainingDayService.createTrainingDay(request,creator);
        ApiResponse<TrainingDay> response = ApiResponse.success("Training day created successfully", trainingDay, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PERSONAL_TRAINER', 'AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<List<TrainingDay>>> getAllTrainingDays() {
        List<TrainingDay> trainingDays = trainingDayService.getAllTrainingDays();
        ApiResponse<List<TrainingDay>> response = ApiResponse.success("Training days retrieved successfully", trainingDays, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{trainingDayId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> updateTrainingDay(
            @PathVariable Long trainingDayId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTrainingDayRequest request) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        TrainingDay trainingDay = trainingDayService.updateTrainingDay(trainingDayId, request, user);
        ApiResponse<TrainingDay> response = ApiResponse.success("Training day updated successfully", trainingDay, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trainingDayId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Void>> deleteTrainingDay(
            @PathVariable Long trainingDayId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        trainingDayService.deleteTrainingDay(trainingDayId, user);
        ApiResponse<Void> response = ApiResponse.success("Training day deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/{trainingDayId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PERSONAL_TRAINER', 'AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> getTrainingDayById(
            @PathVariable Long trainingDayId) {
        TrainingDay trainingDay = trainingDayService.getTrainingDayById(trainingDayId);
        ApiResponse<TrainingDay> response = ApiResponse.success("Training day retrieved successfully", trainingDay, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{trainingDayId}/exercise")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> addExerciseToTrainingDay(
            @PathVariable Long trainingDayId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTrainingDayExerciseRequest request) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        TrainingDay trainingDay = trainingDayService.addExerciseToTrainingDay(trainingDayId, request, user);
        ApiResponse<TrainingDay> response = ApiResponse.success("Exercise added to training day successfully", trainingDay, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{trainingDayId}/exercise/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> updateExerciseInTrainingDay(
            @PathVariable Long trainingDayId,
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTrainingDayExerciseRequest request) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        TrainingDay trainingDay = trainingDayService.updateExerciseInTrainingDay(trainingDayId, exerciseId, request, user);
        ApiResponse<TrainingDay> response = ApiResponse.success("Exercise updated in training day successfully", trainingDay, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trainingDayId}/exercise/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> removeExerciseFromTrainingDay(
            @PathVariable Long trainingDayId,
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        TrainingDay trainingDay = trainingDayService.removeExerciseFromTrainingDay(trainingDayId, exerciseId, user);
        ApiResponse<TrainingDay> response = ApiResponse.success("Exercise removed from training day successfully", trainingDay, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}