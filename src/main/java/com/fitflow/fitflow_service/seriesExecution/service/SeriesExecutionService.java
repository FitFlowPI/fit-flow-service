package com.fitflow.fitflow_service.seriesExecution.service;

import com.fitflow.fitflow_service.common.exception.ResourceNotFoundException;
import com.fitflow.fitflow_service.exerciseExecution.model.ExerciseExecution;
import com.fitflow.fitflow_service.exerciseExecution.repository.ExerciseExecutionRepository;
import com.fitflow.fitflow_service.seriesExecution.dto.CreateSeriesExecutionRequest;
import com.fitflow.fitflow_service.seriesExecution.model.SeriesExecution;
import com.fitflow.fitflow_service.seriesExecution.repository.SeriesExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesExecutionService {

    private final SeriesExecutionRepository seriesExecutionRepository;
    private final ExerciseExecutionRepository exerciseExecutionRepository;

    public SeriesExecution createSeriesExecution(CreateSeriesExecutionRequest request, Long exerciseExecutionId) {
        ExerciseExecution exerciseExecution = exerciseExecutionRepository.findById(exerciseExecutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise execution not found"));
        SeriesExecution seriesExecution = new SeriesExecution();
        seriesExecution.setExerciseExecution(exerciseExecution);
        seriesExecution.setSeriesNumber(request.getSeriesNumber());
        seriesExecution.setActualRepetitions(request.getActualRepetitions());
        seriesExecution.setActualWeight(request.getActualWeight());
        seriesExecution.setActualRestInterval(request.getActualRestInterval());
        seriesExecution.setCompleted(request.getCompleted());
        seriesExecution.setNotes(request.getNotes());
        return seriesExecutionRepository.save(seriesExecution);
    }

    public SeriesExecution getSeriesExecutionById(Long id) {
        return seriesExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series execution not found"));
    }

    public List<SeriesExecution> getAllSeriesExecutions() {
        return seriesExecutionRepository.findAll();
    }

    public SeriesExecution updateSeriesExecution(Long id, CreateSeriesExecutionRequest request) {
        SeriesExecution seriesExecution = seriesExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series execution not found"));

        seriesExecution.setSeriesNumber(request.getSeriesNumber());
        seriesExecution.setActualRepetitions(request.getActualRepetitions());
        seriesExecution.setActualWeight(request.getActualWeight());
        seriesExecution.setActualRestInterval(request.getActualRestInterval());
        seriesExecution.setCompleted(request.getCompleted());
        seriesExecution.setNotes(request.getNotes());

        return seriesExecutionRepository.save(seriesExecution);
    }

    public void deleteSeriesExecution(Long id) {
        SeriesExecution seriesExecution = seriesExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series execution not found"));
        seriesExecutionRepository.delete(seriesExecution);
    }
}