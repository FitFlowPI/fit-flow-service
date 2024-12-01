package com.fitflow.fitflow_service.common.exception;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.exercise.exception.ExerciseNotFoundException;
import com.fitflow.fitflow_service.exercise.exception.ExerciseAlreadyExistsException;
import com.fitflow.fitflow_service.exercise.exception.InvalidExerciseDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleExerciseNotFoundException(ExerciseNotFoundException ex) {
        ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ExerciseAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleExerciseAlreadyExistsException(ExerciseAlreadyExistsException ex) {
        ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidExerciseDataException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidExerciseDataException(InvalidExerciseDataException ex) {
        ApiResponse<?> response = new ApiResponse<>(false, ex.getMessage(), null, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiResponse<?> response = new ApiResponse<>(false, "An unexpected error occurred", null, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
