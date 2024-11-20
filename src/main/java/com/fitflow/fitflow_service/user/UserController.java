package com.fitflow.fitflow_service.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserWeightHistoryService userWeightHistoryService;

    @PutMapping("/weight")
    public ResponseEntity<String> updateWeight(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateWeightRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setWeight(request.getNewWeight());
        userRepository.save(user);

        userWeightHistoryService.saveUserWeightHistory(user.getId(), request.getNewWeight());

        return ResponseEntity.ok("Weight updated successfully");
    }
}