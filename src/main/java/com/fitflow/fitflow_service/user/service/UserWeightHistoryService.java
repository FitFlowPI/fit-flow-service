package com.fitflow.fitflow_service.user.service;

import com.fitflow.fitflow_service.user.model.UserWeightHistory;
import com.fitflow.fitflow_service.user.repository.UserWeightHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserWeightHistoryService {

    private final UserWeightHistoryRepository userWeightHistoryRepository;

    public void saveUserWeightHistory(Long userId, Float weight) {
        UserWeightHistory userWeightHistory = new UserWeightHistory();
        userWeightHistory.setUserId(userId);
        userWeightHistory.setWeight(weight);
        userWeightHistoryRepository.save(userWeightHistory);
    }
}