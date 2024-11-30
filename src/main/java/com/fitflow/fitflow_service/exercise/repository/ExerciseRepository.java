package com.fitflow.fitflow_service.exercise.repository;

import com.fitflow.fitflow_service.exercise.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCreatorId(Long creatorId);
}