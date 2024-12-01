package com.fitflow.fitflow_service.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidExerciseDataException extends RuntimeException {

    public InvalidExerciseDataException(String message) {
        super(message);
    }
}
