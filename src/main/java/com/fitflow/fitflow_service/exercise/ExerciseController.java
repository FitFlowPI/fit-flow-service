package com.fitflow.fitflow_service.exercise;

import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import com.fitflow.fitflow_service.user.enums.UserType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    /*private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createExercise(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateExerciseRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getUser_type() != UserType.personal_trainer && user.getUser_type() != UserType.auto_trainer) {
            return ResponseEntity.status(403).body("Access denied");
        }

        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setMedia(request.getMedia());
        exercise.setCurrentWeight(request.getCurrentWeight());
        exercise.setRestInterval(request.getRestInterval());

        exerciseRepository.save(exercise);

        return ResponseEntity.ok("Exercise created successfully");
    }*/
}