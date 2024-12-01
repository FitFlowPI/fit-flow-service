package com.fitflow.fitflow_service.trainingDay.service;

import com.fitflow.fitflow_service.exercise.model.Exercise;
import com.fitflow.fitflow_service.exercise.repository.ExerciseRepository;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayExerciseRequest;
import com.fitflow.fitflow_service.trainingDay.dto.CreateTrainingDayRequest;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDayExercise;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDayExerciseId;
import com.fitflow.fitflow_service.trainingDay.repository.TrainingDayExerciseRepository;
import com.fitflow.fitflow_service.trainingDay.repository.TrainingDayRepository;
import com.fitflow.fitflow_service.user.model.User;
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

    public TrainingDay createTrainingDay(CreateTrainingDayRequest request,User creator) {

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
            trainingDayExercise.setDefaultSeries(exerciseRequest.getSeries());
            trainingDayExercise.setDefaultRepetitions(exerciseRequest.getRepetitions());
            trainingDayExercise.setDefaultWeight(exerciseRequest.getWeight());
            trainingDayExercise.setDefaultRestInterval(exerciseRequest.getRestInterval());
            trainingDayExerciseRepository.save(trainingDayExercise);
        }

        return trainingDay;
    }

    public List<TrainingDay> getAllTrainingDays() {
        return trainingDayRepository.findAll();
    }

    public TrainingDay updateTrainingDay(Long trainingDayId, CreateTrainingDayRequest request, User user) {
        TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));

        if (!trainingDay.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot update a training day they didn't create");
        }

        trainingDay.setName(request.getName());
        trainingDay.getExercises().clear();

        for (CreateTrainingDayExerciseRequest exerciseRequest : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exerciseRequest.getExerciseId())
                    .orElseThrow(() -> new RuntimeException("Exercise not found"));

            TrainingDayExercise trainingDayExercise = new TrainingDayExercise();
            trainingDayExercise.setTrainingDay(trainingDay);
            trainingDayExercise.setExercise(exercise);
            trainingDayExercise.setDefaultSeries(exerciseRequest.getSeries());
            trainingDayExercise.setDefaultRepetitions(exerciseRequest.getRepetitions());
            trainingDayExercise.setDefaultWeight(exerciseRequest.getWeight());
            trainingDayExercise.setDefaultRestInterval(exerciseRequest.getRestInterval());
            trainingDay.getExercises().add(trainingDayExercise);
        }

        return trainingDayRepository.save(trainingDay);
    }

    public void deleteTrainingDay(Long trainingDayId, User user) {
        TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));

        if (!trainingDay.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot delete a training day they didn't create");
        }

        trainingDayRepository.delete(trainingDay);
    }

    public TrainingDay getTrainingDayById(Long trainingDayId) {
        return trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));
    }

    public TrainingDay addExerciseToTrainingDay(Long trainingDayId, CreateTrainingDayExerciseRequest exerciseRequest, User user) {
        TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));

        if (!trainingDay.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot modify a training day they didn't create");
        }

        Exercise exercise = exerciseRepository.findById(exerciseRequest.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        TrainingDayExercise trainingDayExercise = new TrainingDayExercise();
        trainingDayExercise.setTrainingDay(trainingDay);
        trainingDayExercise.setExercise(exercise);
        trainingDayExercise.setDefaultSeries(exerciseRequest.getSeries());
        trainingDayExercise.setDefaultRepetitions(exerciseRequest.getRepetitions());
        trainingDayExercise.setDefaultWeight(exerciseRequest.getWeight());
        trainingDayExercise.setDefaultRestInterval(exerciseRequest.getRestInterval());
        trainingDay.getExercises().add(trainingDayExercise);

        return trainingDayRepository.save(trainingDay);
    }

    public TrainingDay updateExerciseInTrainingDay(Long trainingDayId, Long exerciseId, CreateTrainingDayExerciseRequest exerciseRequest, User user) {
        TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));

        if (!trainingDay.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot modify a training day they didn't create");
        }

        TrainingDayExerciseId trainingDayExerciseId = new TrainingDayExerciseId(trainingDayId, exerciseId);
        TrainingDayExercise trainingDayExercise = trainingDayExerciseRepository.findById(trainingDayExerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found in training day"));

        trainingDayExercise.setDefaultSeries(exerciseRequest.getSeries());
        trainingDayExercise.setDefaultRepetitions(exerciseRequest.getRepetitions());
        trainingDayExercise.setDefaultWeight(exerciseRequest.getWeight());
        trainingDayExercise.setDefaultRestInterval(exerciseRequest.getRestInterval());

        return trainingDayRepository.save(trainingDay);
    }

    public TrainingDay removeExerciseFromTrainingDay(Long trainingDayId, Long exerciseId, User user) {
        TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                .orElseThrow(() -> new RuntimeException("Training day not found"));

        if (!trainingDay.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot modify a training day they didn't create");
        }

        TrainingDayExerciseId trainingDayExerciseId = new TrainingDayExerciseId(trainingDayId, exerciseId);
        TrainingDayExercise trainingDayExercise = trainingDayExerciseRepository.findById(trainingDayExerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found in training day"));

        trainingDay.getExercises().remove(trainingDayExercise);
        trainingDayExerciseRepository.delete(trainingDayExercise);

        return trainingDayRepository.save(trainingDay);
    }
}