package com.fitflow.fitflow_service.exerciseExecution.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "exercise_execution")
public class ExerciseExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "exercise_id", nullable = false)
    private Long exerciseId;

    @Column(name = "weight_used")
    private Double weightUsed;

    @Column(name = "repetitions")
    private Integer repetitions;

    @Column(name = "rest_time")
    private Integer restTime; // Tempo de descanso em segundos

    @Column(name = "execution_date", nullable = false)
    private Date executionDate;

    @Column(name = "execution_timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime executionTimestamp;

    @Column(name = "series_number")
    private Integer seriesNumber;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "actual_rest_interval")
    private Integer actualRestInterval;

    @Column(name = "notes")
    private String notes;
}