package com.fitflow.fitflow_service.trainingProgram.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.trainingProgram.dto.CreateTrainingProgramRequest;
import com.fitflow.fitflow_service.trainingProgram.dto.UpdateTrainingProgramRequest;
import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgram;
import com.fitflow.fitflow_service.trainingProgram.service.TrainingProgramService;
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
@RequestMapping("/api/v1/training-program")
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingProgram>> createTrainingProgram(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateTrainingProgramRequest request) {
        User creator = userService.findUserByEmail(userDetails.getUsername());
        TrainingProgram trainingProgram = trainingProgramService.createTrainingProgram(request, creator);
        ApiResponse<TrainingProgram> response = ApiResponse.success("Training program created successfully", trainingProgram, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingProgram>> updateTrainingProgram(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateTrainingProgramRequest request) {
        User creator = userService.findUserByEmail(userDetails.getUsername());
        TrainingProgram trainingProgram = trainingProgramService.updateTrainingProgram(id, request, creator);
        ApiResponse<TrainingProgram> response = ApiResponse.success("Training program updated successfully", trainingProgram, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<List<TrainingProgram>>> getAllTrainingPrograms() {
        List<TrainingProgram> trainingPrograms = trainingProgramService.getAllTrainingPrograms();
        ApiResponse<List<TrainingProgram>> response = ApiResponse.success("Training programs retrieved successfully", trainingPrograms, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingProgram>> getTrainingProgramById(@PathVariable Long id) {
        TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramById(id);
        ApiResponse<TrainingProgram> response = ApiResponse.success("Training program retrieved successfully", trainingProgram, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Void>> deleteTrainingProgram(@PathVariable Long id) {
        trainingProgramService.deleteTrainingProgram(id);
        ApiResponse<Void> response = ApiResponse.success("Training program deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}