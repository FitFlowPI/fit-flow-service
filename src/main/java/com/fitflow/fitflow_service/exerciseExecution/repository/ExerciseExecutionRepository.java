package com.fitflow.fitflow_service.exerciseExecution.repository;

import com.fitflow.fitflow_service.exerciseExecution.model.ExerciseExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseExecutionRepository extends JpaRepository<ExerciseExecution, Long> {
    List<ExerciseExecution> findByUserId(Long userId);
    List<ExerciseExecution> findByExerciseId(Long exerciseId);
}
