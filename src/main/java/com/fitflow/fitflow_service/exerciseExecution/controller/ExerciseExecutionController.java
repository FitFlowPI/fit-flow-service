package com.fitflow.fitflow_service.exerciseExecution.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.exerciseExecution.dto.CreateExerciseExecutionRequest;
import com.fitflow.fitflow_service.exerciseExecution.dto.UpdateExerciseExecutionRequest;
import com.fitflow.fitflow_service.exerciseExecution.model.ExerciseExecution;
import com.fitflow.fitflow_service.exerciseExecution.service.ExerciseExecutionService;
import com.fitflow.fitflow_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercise-execution")
public class ExerciseExecutionController {

    private final ExerciseExecutionService exerciseExecutionService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ExerciseExecution>> createExerciseExecution(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateExerciseExecutionRequest request) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        ExerciseExecution exerciseExecution = exerciseExecutionService.createExerciseExecution(request, user.getId());
        ApiResponse<ExerciseExecution> response = ApiResponse.success("Exercise execution created successfully", exerciseExecution, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<ExerciseExecution>>> getExerciseExecutionsByUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        List<ExerciseExecution> executions = exerciseExecutionService.getExerciseExecutionsByUser(user.getId());
        ApiResponse<List<ExerciseExecution>> response = ApiResponse.success("Executions retrieved successfully", executions, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExerciseExecution>> updateExerciseExecution(
            @PathVariable Long id,
            @RequestBody UpdateExerciseExecutionRequest request) {
        ExerciseExecution exerciseExecution = exerciseExecutionService.updateExerciseExecution(id, request);
        ApiResponse<ExerciseExecution> response = ApiResponse.success("Execution updated successfully", exerciseExecution, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExerciseExecution(@PathVariable Long id) {
        exerciseExecutionService.deleteExerciseExecution(id);
        ApiResponse<Void> response = ApiResponse.success("Execution deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
