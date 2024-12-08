package com.fitflow.fitflow_service.exerciseExecution.service;

import com.fitflow.fitflow_service.common.exception.ResourceNotFoundException;
import com.fitflow.fitflow_service.exerciseExecution.dto.CreateExerciseExecutionRequest;
import com.fitflow.fitflow_service.exerciseExecution.dto.UpdateExerciseExecutionRequest;
import com.fitflow.fitflow_service.exerciseExecution.model.ExerciseExecution;
import com.fitflow.fitflow_service.exerciseExecution.repository.ExerciseExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseExecutionService {

    private final ExerciseExecutionRepository exerciseExecutionRepository;

    public ExerciseExecution createExerciseExecution(CreateExerciseExecutionRequest request, Long userId) {
        ExerciseExecution exerciseExecution = new ExerciseExecution();
        exerciseExecution.setUserId(userId);
        exerciseExecution.setExerciseId(request.getExerciseId());
        exerciseExecution.setWeightUsed(request.getWeightUsed());
        exerciseExecution.setRepetitions(request.getRepetitions());
        exerciseExecution.setRestTime(request.getRestTime());
        exerciseExecution.setSeriesNumber(request.getSeriesNumber());
        exerciseExecution.setCompleted(request.getCompleted());
        exerciseExecution.setActualRestInterval(request.getActualRestInterval());
        exerciseExecution.setNotes(request.getNotes());
        exerciseExecution.setExecutionDate(request.getExecutionDate());
        exerciseExecution.setExecutionTimestamp(request.getExecutionTimestamp() != null
                ? request.getExecutionTimestamp()
                : LocalDateTime.now()); // Usa o timestamp atual se não for fornecido
        return exerciseExecutionRepository.save(exerciseExecution);
    }

    public List<ExerciseExecution> getExerciseExecutionsByUser(Long userId) {
        return exerciseExecutionRepository.findByUserId(userId);
    }

    public List<ExerciseExecution> getExerciseExecutionsByExercise(Long exerciseId) {
        return exerciseExecutionRepository.findByExerciseId(exerciseId);
    }

    public ExerciseExecution updateExerciseExecution(Long id, UpdateExerciseExecutionRequest request) {
        ExerciseExecution exerciseExecution = exerciseExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise execution not found"));

        exerciseExecution.setWeightUsed(request.getWeightUsed());
        exerciseExecution.setRepetitions(request.getRepetitions());
        exerciseExecution.setRestTime(request.getRestTime());
        exerciseExecution.setSeriesNumber(request.getSeriesNumber());
        exerciseExecution.setCompleted(request.getCompleted());
        exerciseExecution.setActualRestInterval(request.getActualRestInterval());
        exerciseExecution.setNotes(request.getNotes());
        if (request.getExecutionTimestamp() != null) {
            exerciseExecution.setExecutionTimestamp(request.getExecutionTimestamp());
        }
        return exerciseExecutionRepository.save(exerciseExecution);
    }

    public void deleteExerciseExecution(Long id) {
        ExerciseExecution exerciseExecution = exerciseExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise execution not found"));
        exerciseExecutionRepository.delete(exerciseExecution);
    }
}
