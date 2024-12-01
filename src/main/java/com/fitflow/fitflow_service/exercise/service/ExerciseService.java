package com.fitflow.fitflow_service.exercise.service;

import com.fitflow.fitflow_service.exercise.dto.CreateExerciseRequest;
import com.fitflow.fitflow_service.exercise.dto.UpdateExerciseRequest;
import com.fitflow.fitflow_service.exercise.exception.ExerciseNotFoundException;
import com.fitflow.fitflow_service.exercise.exception.UnauthorizedExerciseAccessException;
import com.fitflow.fitflow_service.exercise.model.Exercise;
import com.fitflow.fitflow_service.exercise.repository.ExerciseRepository;
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
            throw new UnauthorizedExerciseAccessException("User type not allowed to view exercises");
        }
    }

    public Exercise getExerciseById(Long exerciseId, User user) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found"));

        if (!exercise.getCreator().getId().equals(user.getId()) && user.getUser_type() == UserType.AUTO_TRAINER) {
            throw new UnauthorizedExerciseAccessException("Auto Trainer cannot view an exercise they didn't create");
        }

        return exercise;
    }

    public Exercise updateExercise(Long exerciseId, UpdateExerciseRequest request, User user) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found"));

        if (!exercise.getCreator().getId().equals(user.getId()) && user.getUser_type() == UserType.AUTO_TRAINER) {
            throw new UnauthorizedExerciseAccessException("Auto Trainer cannot update an exercise they didn't create");
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
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found"));

        if (!exercise.getCreator().getId().equals(user.getId()) && user.getUser_type() == UserType.AUTO_TRAINER) {
            throw new UnauthorizedExerciseAccessException("Auto Trainer cannot delete an exercise they didn't create");
        }

        exerciseRepository.delete(exercise);
    }
}