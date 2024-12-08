package com.fitflow.fitflow_service.trainingProgram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitflow.fitflow_service.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "training_programs")
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private User creator;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TrainingProgramDay> trainingProgramDays = new ArrayList<>();
}