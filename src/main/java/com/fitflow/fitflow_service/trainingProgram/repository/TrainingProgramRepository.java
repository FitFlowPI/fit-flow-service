package com.fitflow.fitflow_service.trainingProgram.repository;

import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
}