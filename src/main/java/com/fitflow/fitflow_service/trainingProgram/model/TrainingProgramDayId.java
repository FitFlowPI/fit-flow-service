package com.fitflow.fitflow_service.trainingProgram.model;

import java.io.Serializable;
import java.util.Objects;

public class TrainingProgramDayId implements Serializable {

    private Long trainingProgram;
    private Long trainingDay;

    public TrainingProgramDayId() {}

    public TrainingProgramDayId(Long trainingProgram, Long trainingDay) {
        this.trainingProgram = trainingProgram;
        this.trainingDay = trainingDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingProgramDayId that = (TrainingProgramDayId) o;
        return Objects.equals(trainingProgram, that.trainingProgram) && Objects.equals(trainingDay, that.trainingDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingProgram, trainingDay);
    }
}