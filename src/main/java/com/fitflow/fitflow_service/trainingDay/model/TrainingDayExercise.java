package com.fitflow.fitflow_service.trainingDay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitflow.fitflow_service.exercise.model.Exercise;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_day_exercises")
@IdClass(TrainingDayExerciseId.class)
public class TrainingDayExercise {

    @Id
    @ManyToOne
    @JoinColumn(name = "training_day_id", nullable = false)
    private TrainingDay trainingDay;

    @Id
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    @JsonIgnore
    private Exercise exercise;

    @Column(name = "default_series", nullable = false)
    private Integer defaultSeries;

    @Column(name = "default_repetitions", nullable = false)
    private Integer defaultRepetitions;

    @Column(name = "default_weight")
    private Double defaultWeight;

    @Column(name = "default_rest_interval")
    private Integer defaultRestInterval;
}