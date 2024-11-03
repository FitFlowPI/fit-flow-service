package com.fitflow.fitflow_service.user;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_weight_history")
public class UserWeightHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "recorded_date", nullable = false)
    private LocalDate recordedDate = LocalDate.now();
}