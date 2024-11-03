package com.fitflow.fitflow_service.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateWeightRequest {
    private BigDecimal newWeight;
}