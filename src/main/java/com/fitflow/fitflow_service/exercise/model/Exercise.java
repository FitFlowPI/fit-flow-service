package com.fitflow.fitflow_service.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitflow.fitflow_service.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String media; // URL para imagem/vídeo

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "rest_interval")
    private Integer restInterval; // em segundos

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private User creator;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}