package com.fitflow.fitflow_service.user.service;

import com.fitflow.fitflow_service.common.exception.UserNotFoundException;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWeightHistoryService userWeightHistoryService;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    public void updateWeight(User user, Float newWeight) {
        user.setWeight(newWeight);
        userRepository.save(user);
        userWeightHistoryService.saveUserWeightHistory(user.getId(), newWeight);
    }
}


