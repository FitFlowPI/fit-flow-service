package com.fitflow.fitflow_service.trainingDay.repository;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrainingDayRepository extends JpaRepository<TrainingDay, Long> {
}