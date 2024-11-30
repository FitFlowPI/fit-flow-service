package com.fitflow.fitflow_service.user.repository;

import com.fitflow.fitflow_service.user.model.UserWeightHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWeightHistoryRepository extends JpaRepository<UserWeightHistory, Long> {
}