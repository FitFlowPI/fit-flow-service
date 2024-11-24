package com.fitflow.fitflow_service.trainingDay.repository;

import com.fitflow.fitflow_service.trainingDay.model.TrainingDayExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDayExerciseRepository extends JpaRepository<TrainingDayExercise, Long> {
}
