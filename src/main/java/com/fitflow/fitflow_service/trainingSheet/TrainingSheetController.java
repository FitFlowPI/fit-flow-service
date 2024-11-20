package com.fitflow.fitflow_service.trainingSheet;

import com.fitflow.fitflow_service.trainingSheet.TrainingSheet;
import com.fitflow.fitflow_service.trainingSheet.TrainingSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/training-sheet")
public class TrainingSheetController {

    private final TrainingSheetService trainingSheetService;

    @PostMapping("/create")
    public ResponseEntity<TrainingSheet> createTrainingSheet(@RequestBody CreateTrainingSheetRequest request) {
        TrainingSheet trainingSheet = trainingSheetService.createTrainingSheet(
                request.getName(), request.getCreatorId(), request.getExerciseIds());
        return ResponseEntity.ok(trainingSheet);
    }

    @GetMapping
    public ResponseEntity<List<TrainingSheet>> getAllTrainingSheets() {
        List<TrainingSheet> trainingSheets = trainingSheetService.getAllTrainingSheets();
        return ResponseEntity.ok(trainingSheets);
    }
}