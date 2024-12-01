package com.fitflow.fitflow_service.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExerciseAlreadyExistsException extends RuntimeException {

  public ExerciseAlreadyExistsException(String message) {
    super(message);
  }
}
