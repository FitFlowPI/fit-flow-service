package com.fitflow.fitflow_service.trainingProgram.repository;

import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgramDay;
import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgramDayId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingProgramDayRepository extends JpaRepository<TrainingProgramDay, TrainingProgramDayId> {
}