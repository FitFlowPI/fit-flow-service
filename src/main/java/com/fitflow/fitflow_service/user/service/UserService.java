package com.fitflow.fitflow_service.user.service;

import com.fitflow.fitflow_service.common.exception.ResourceNotFoundException;
import com.fitflow.fitflow_service.user.enums.Gender;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWeightHistoryService userWeightHistoryService;
    private final PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public void updateName(User user, String newName) {
        user.setName(newName);
        userRepository.save(user);
    }

    public void updateWeight(User user, Float newWeight) {
        user.setWeight(newWeight);
        userRepository.save(user);
        userWeightHistoryService.saveUserWeightHistory(user.getId(), newWeight);
    }

    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateGender(User user, String newGender) {
        user.setGender(Gender.valueOf(newGender.toUpperCase()));
        userRepository.save(user);
    }

    public void updateHeight(User user, Float newHeight) {
        user.setHeight(newHeight);
        userRepository.save(user);
    }
}