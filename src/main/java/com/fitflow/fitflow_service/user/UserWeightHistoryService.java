package com.fitflow.fitflow_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserWeightHistoryService {

    private final UserWeightHistoryRepository userWeightHistoryRepository;

    public void saveUserWeightHistory(Long userId, BigDecimal weight) {
        UserWeightHistory userWeightHistory = new UserWeightHistory();
        userWeightHistory.setUserId(userId);
        userWeightHistory.setWeight(weight);
        userWeightHistoryRepository.save(userWeightHistory);
    }
}