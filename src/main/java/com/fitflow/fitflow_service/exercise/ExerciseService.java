package com.fitflow.fitflow_service.exercise;

import com.fitflow.fitflow_service.user.enums.UserType;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public Exercise createExercise(CreateExerciseRequest request, User creator) {
        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setMedia(request.getMedia());
        exercise.setCurrentWeight(request.getCurrentWeight());
        exercise.setRestInterval(request.getRestInterval());
        exercise.setCreator(creator);
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getExercises(User user) {
        if (user.getUser_type() == UserType.PERSONAL_TRAINER) {
            return exerciseRepository.findAll();
        } else if (user.getUser_type() == UserType.AUTO_TRAINER) {
            return exerciseRepository.findByCreatorId(user.getId());
        } else {
            throw new RuntimeException("User type not allowed to view exercises");
        }
    }

    public Exercise updateExercise(Long exerciseId, UpdateExerciseRequest request, User user) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        if (!exercise.getCreator().getId().equals(user.getId()) && user.getUser_type() == UserType.AUTO_TRAINER) {
            throw new RuntimeException("Auto Trainer cannot update an exercise they didn't create");
        }

        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setMedia(request.getMedia());
        exercise.setCurrentWeight(request.getCurrentWeight());
        exercise.setRestInterval(request.getRestInterval());
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long exerciseId, User user) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        if (!exercise.getCreator().getId().equals(user.getId()) && user.getUser_type() == UserType.AUTO_TRAINER) {
            throw new RuntimeException("Auto Trainer cannot delete an exercise they didn't create");
        }

        exerciseRepository.delete(exercise);
    }
}

