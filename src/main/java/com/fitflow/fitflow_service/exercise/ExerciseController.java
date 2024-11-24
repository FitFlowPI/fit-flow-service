package com.fitflow.fitflow_service.exercise;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import com.fitflow.fitflow_service.user.enums.UserType;
import com.fitflow.fitflow_service.user.service.UserService;
import lombok.AllArgsConstructor;
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
        ApiResponse<Exercise> response = new ApiResponse<>(true, "Exercise created successfully", exercise, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<List<Exercise>>> getExercises(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        List<Exercise> exercises = exerciseService.getExercises(user);
        ApiResponse<List<Exercise>> response = new ApiResponse<>(true, "Exercises retrieved successfully", exercises, HttpStatus.OK.value());
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
        ApiResponse<Exercise> response = new ApiResponse<>(true, "Exercise updated successfully", exercise, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{exerciseId}")
    @PreAuthorize("hasRole('PERSONAL_TRAINER') or hasRole('AUTO_TRAINER')")
    public ResponseEntity<ApiResponse<Void>> deleteExercise(
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        exerciseService.deleteExercise(exerciseId, user);
        ApiResponse<Void> response = new ApiResponse<>(true, "Exercise deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
