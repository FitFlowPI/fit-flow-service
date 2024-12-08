package com.fitflow.fitflow_service.trainingProgram.service;

import com.fitflow.fitflow_service.common.exception.ResourceNotFoundException;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import com.fitflow.fitflow_service.trainingDay.repository.TrainingDayRepository;
import com.fitflow.fitflow_service.trainingProgram.dto.CreateTrainingProgramRequest;
import com.fitflow.fitflow_service.trainingProgram.dto.UpdateTrainingProgramRequest;
import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgram;
import com.fitflow.fitflow_service.trainingProgram.model.TrainingProgramDay;
import com.fitflow.fitflow_service.trainingProgram.repository.TrainingProgramDayRepository;
import com.fitflow.fitflow_service.trainingProgram.repository.TrainingProgramRepository;
import com.fitflow.fitflow_service.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramDayRepository trainingProgramDayRepository;
    private final TrainingDayRepository trainingDayRepository;

    public TrainingProgram createTrainingProgram(CreateTrainingProgramRequest request, User creator) {
        TrainingProgram trainingProgram = new TrainingProgram();
        trainingProgram.setName(request.getName());
        trainingProgram.setDescription(request.getDescription());
        trainingProgram.setCreationDate(new Date());
        trainingProgram.setCreator(creator);
        trainingProgram = trainingProgramRepository.save(trainingProgram);

        int dayOrder = 1;
        for (Long trainingDayId : request.getTrainingDayIds()) {
            TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                    .orElseThrow(() -> new ResourceNotFoundException("Training day not found"));

            TrainingProgramDay trainingProgramDay = new TrainingProgramDay();
            trainingProgramDay.setTrainingProgram(trainingProgram);
            trainingProgramDay.setTrainingDay(trainingDay);
            trainingProgramDay.setDayOrder(dayOrder++);
            trainingProgramDayRepository.save(trainingProgramDay);
        }

        return trainingProgram;
    }

    public TrainingProgram updateTrainingProgram(Long id, UpdateTrainingProgramRequest request, User creator) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training program not found"));

        trainingProgram.setName(request.getName());
        trainingProgram.setDescription(request.getDescription());
        trainingProgram.setCreationDate(new Date());
        trainingProgram.setCreator(creator);
        trainingProgram = trainingProgramRepository.save(trainingProgram);

        trainingProgramDayRepository.deleteAll(trainingProgram.getTrainingProgramDays());
        trainingProgram.getTrainingProgramDays().clear();

        int dayOrder = 1;
        for (Long trainingDayId : request.getTrainingDayIds()) {
            TrainingDay trainingDay = trainingDayRepository.findById(trainingDayId)
                    .orElseThrow(() -> new ResourceNotFoundException("Training day not found"));

            TrainingProgramDay trainingProgramDay = new TrainingProgramDay();
            trainingProgramDay.setTrainingProgram(trainingProgram);
            trainingProgramDay.setTrainingDay(trainingDay);
            trainingProgramDay.setDayOrder(dayOrder++);
            trainingProgramDayRepository.save(trainingProgramDay);
        }

        return trainingProgram;
    }

    public List<TrainingProgram> getAllTrainingPrograms() {
        return trainingProgramRepository.findAll();
    }

    public TrainingProgram getTrainingProgramById(Long id) {
        return trainingProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training program not found"));
    }
}