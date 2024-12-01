package com.fitflow.fitflow_service.trainingDay.model;

import java.io.Serializable;
import java.util.Objects;

public class TrainingDayExerciseId implements Serializable {

    private Long trainingDay;
    private Long exercise;

    public TrainingDayExerciseId() {}

    public TrainingDayExerciseId(Long trainingDay, Long exercise) {
        this.trainingDay = trainingDay;
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingDayExerciseId that = (TrainingDayExerciseId) o;
        return Objects.equals(trainingDay, that.trainingDay) && Objects.equals(exercise, that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingDay, exercise);
    }
}