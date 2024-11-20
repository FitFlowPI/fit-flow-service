package com.fitflow.fitflow_service.trainingSheet;

import com.fitflow.fitflow_service.exercise.Exercise;
import com.fitflow.fitflow_service.exercise.ExerciseRepository;
import com.fitflow.fitflow_service.trainingSheet.TrainingSheetExerciseRepository;
import com.fitflow.fitflow_service.trainingSheet.TrainingSheetRepository;
import com.fitflow.fitflow_service.user.User;
import com.fitflow.fitflow_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSheetService {

    private final TrainingSheetRepository trainingSheetRepository;
    private final TrainingSheetExerciseRepository trainingSheetExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public TrainingSheet createTrainingSheet(String name, Long creatorId, List<Long> exerciseIds) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        TrainingSheet trainingSheet = new TrainingSheet();
        trainingSheet.setName(name);
        trainingSheet.setCreationDate(new Date());
        trainingSheet.setCreator(creator);
        trainingSheet = trainingSheetRepository.save(trainingSheet);

        for (Long exerciseId : exerciseIds) {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new RuntimeException("Exercise not found"));

            TrainingSheetExercise trainingSheetExercise = new TrainingSheetExercise();
            trainingSheetExercise.setTrainingSheet(trainingSheet);
            trainingSheetExercise.setExercise(exercise);
            trainingSheetExerciseRepository.save(trainingSheetExercise);
        }

        return trainingSheet;
    }

    public List<TrainingSheet> getAllTrainingSheets() {
        return trainingSheetRepository.findAll();
    }
}