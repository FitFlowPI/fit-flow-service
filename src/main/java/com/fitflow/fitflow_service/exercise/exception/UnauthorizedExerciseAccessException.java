package com.fitflow.fitflow_service.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedExerciseAccessException extends RuntimeException {
    public UnauthorizedExerciseAccessException(String message) {
        super(message);
    }
}
