package com.fitflow.fitflow_service.trainingSheet;

import com.fitflow.fitflow_service.exercise.Exercise;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_sheet_exercises")
public class TrainingSheetExercise {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_sheet_id", nullable = false)
    private TrainingSheet trainingSheet;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}