package com.fitflow.fitflow_service.trainingProgram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitflow.fitflow_service.trainingDay.model.TrainingDay;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_program_days")
@IdClass(TrainingProgramDayId.class)
public class TrainingProgramDay {

    @Id
    @ManyToOne
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;

    @Id
    @ManyToOne
    @JoinColumn(name = "training_day_id", nullable = false)
    @JsonIgnore
    private TrainingDay trainingDay;

    @Column(name = "day_order", nullable = false)
    private Integer dayOrder;
}