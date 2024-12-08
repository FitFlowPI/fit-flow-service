package com.fitflow.fitflow_service.seriesExecution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitflow.fitflow_service.exerciseExecution.model.ExerciseExecution;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "series_execution")
public class SeriesExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exercise_execution_id", nullable = false)
    @JsonIgnore
    private ExerciseExecution exerciseExecution;

    @Column(name = "series_number", nullable = false)
    private Integer seriesNumber;

    @Column(name = "actual_repetitions")
    private Integer actualRepetitions;

    @Column(name = "actual_weight")
    private Double actualWeight;

    @Column(name = "actual_rest_interval")
    private Integer actualRestInterval;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "notes")
    private String notes;
}