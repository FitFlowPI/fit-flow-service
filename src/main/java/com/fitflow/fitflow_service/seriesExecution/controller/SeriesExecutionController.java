package com.fitflow.fitflow_service.seriesExecution.controller;

import com.fitflow.fitflow_service.common.response.ApiResponse;
import com.fitflow.fitflow_service.seriesExecution.dto.CreateSeriesExecutionRequest;
import com.fitflow.fitflow_service.seriesExecution.model.SeriesExecution;
import com.fitflow.fitflow_service.seriesExecution.service.SeriesExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/series-execution")
public class SeriesExecutionController {

    private final SeriesExecutionService seriesExecutionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SeriesExecution>> createSeriesExecution(
            @RequestParam Long exerciseExecutionId,
            @RequestBody CreateSeriesExecutionRequest request) {
        SeriesExecution seriesExecution = seriesExecutionService.createSeriesExecution(request, exerciseExecutionId);
        ApiResponse<SeriesExecution> response = ApiResponse.success("Series execution created successfully", seriesExecution, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesExecution>> getSeriesExecutionById(@PathVariable Long id) {
        SeriesExecution seriesExecution = seriesExecutionService.getSeriesExecutionById(id);
        ApiResponse<SeriesExecution> response = ApiResponse.success("Series execution retrieved successfully", seriesExecution, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeriesExecution>>> getAllSeriesExecutions() {
        List<SeriesExecution> seriesExecutions = seriesExecutionService.getAllSeriesExecutions();
        ApiResponse<List<SeriesExecution>> response = ApiResponse.success("Series executions retrieved successfully", seriesExecutions, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeriesExecution>> updateSeriesExecution(
            @PathVariable Long id,
            @RequestBody CreateSeriesExecutionRequest request) {
        SeriesExecution seriesExecution = seriesExecutionService.updateSeriesExecution(id, request);
        ApiResponse<SeriesExecution> response = ApiResponse.success("Series execution updated successfully", seriesExecution, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeriesExecution(@PathVariable Long id) {
        seriesExecutionService.deleteSeriesExecution(id);
        ApiResponse<Void> response = ApiResponse.success("Series execution deleted successfully", null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}