package com.fitflow.fitflow_service.trainingDay.model;

import com.fitflow.fitflow_service.exercise.Exercise;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_day_exercises")
public class TrainingDayExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_day_id", nullable = false)
    private TrainingDay trainingDay;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer series;

    @Column(nullable = false)
    private Integer repetitions;

    @Column(nullable = false)
    private Double weight;

    @Column(name = "rest_interval", nullable = false)
    private Integer restInterval;
}