package com.fitflow.fitflow_service.trainingDay.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayRequest;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import com.fitflow.fitflow_service.trainingDay.service.TrainingDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training-day")
public class TrainingDayController {

    private final TrainingDayService trainingDayService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<TrainingDay>> createTrainingDay(@RequestBody CreateTrainingDayRequest request) {
        TrainingDay trainingDay = trainingDayService.createTrainingDay(request);
        ApiResponse<TrainingDay> response = new ApiResponse<>(true, "Training day created successfully", trainingDay, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PERSONAL_TRAINER', 'AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<List<TrainingDay>>> getAllTrainingDays() {
        List<TrainingDay> trainingDays = trainingDayService.getAllTrainingDays();
        ApiResponse<List<TrainingDay>> response = new ApiResponse<>(true, "Training days retrieved successfully", trainingDays, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
