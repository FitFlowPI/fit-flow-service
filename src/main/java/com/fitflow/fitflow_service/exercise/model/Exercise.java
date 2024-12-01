package com.fitflow.fitflow_service.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitflow.fitflow_service.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnoreProperties({"password", "email", "authorities", "active_plan"})
    private User creator;
}