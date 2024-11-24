package com.fitflow.fitflow_service.trainingDay.service;

import com.fitflow.fitflow_service.exercise.Exercise;
import com.fitflow.fitflow_service.exercise.ExerciseRepository;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayExerciseRequest;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayRequest;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDayExercise;
import com.fitflow.fitflow_service.trainingDay.repository.TrainingDayExerciseRepository;
import com.fitflow.fitflow_service.trainingDay.repository.TrainingDayRepository;
import com.fitflow.fitflow_service.user.model.User;
import com.fitflow.fitflow_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDayService {

    private final TrainingDayRepository trainingDayRepository;
    private final TrainingDayExerciseRepository trainingDayExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public TrainingDay createTrainingDay(CreateTrainingDayRequest request) {
        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        TrainingDay trainingDay = new TrainingDay();
        trainingDay.setName(request.getName());
        trainingDay.setCreationDate(new Date());
        trainingDay.setCreator(creator);
        trainingDay = trainingDayRepository.save(trainingDay);

        for (CreateTrainingDayExerciseRequest exerciseRequest : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exerciseRequest.getExerciseId())
                    .orElseThrow(() -> new RuntimeException("Exercise not found"));

            TrainingDayExercise trainingDayExercise = new TrainingDayExercise();
            trainingDayExercise.setTrainingDay(trainingDay);
            trainingDayExercise.setExercise(exercise);
            trainingDayExercise.setSeries(exerciseRequest.getSeries());
            trainingDayExercise.setRepetitions(exerciseRequest.getRepetitions());
            trainingDayExercise.setWeight(exerciseRequest.getWeight());
            trainingDayExercise.setRestInterval(exerciseRequest.getRestInterval());
            trainingDayExerciseRepository.save(trainingDayExercise);
        }

        return trainingDay;
    }

    public List<TrainingDay> getAllTrainingDays() {
        return trainingDayRepository.findAll();
    }
}
