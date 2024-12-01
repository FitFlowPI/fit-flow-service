package com.fitflow.fitflow_service.exercise.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.exercise.dto.CreateExerciseRequest;
import com.fitflow.fitflow_service.exercise.dto.UpdateExerciseRequest;
import com.fitflow.fitflow_service.exercise.model.Exercise;
import com.fitflow.fitflow_service.exercise.service.ExerciseService;
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
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Exercise>> createExercise(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateExerciseRequest request) {
        User creator = userService.findUserByEmail(userDetails.getUsername());
        Exercise exercise = exerciseService.createExercise(request, creator);
        ApiResponse<Exercise> response = ApiResponse.success("Exercise created successfully", exercise, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<List<Exercise>>> getExercises(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        List<Exercise> exercises = exerciseService.getExercises(user);
        ApiResponse<List<Exercise>> response = ApiResponse.success("Exercises retrieved successfully", exercises, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Exercise>> updateExercise(
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateExerciseRequest request) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Exercise exercise = exerciseService.updateExercise(exerciseId, request, user);
        ApiResponse<Exercise> response = ApiResponse.success("Exercise updated successfully", exercise, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Exercise>> getExerciseById(
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Exercise exercise = exerciseService.getExerciseById(exerciseId, user);
        ApiResponse<Exercise> response = ApiResponse.success("Exercise retrieved successfully", exercise, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Void>> deleteExercise(
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        exerciseService.deleteExercise(exerciseId, user);
        ApiResponse<Void> response = ApiResponse.success("Exercise deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}