package com.fitflow.fitflow_service.seriesExecution.repository;

import com.fitflow.fitflow_service.seriesExecution.model.SeriesExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesExecutionRepository extends JpaRepository<SeriesExecution, Long> {
}