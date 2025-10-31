package com.gymapp.workload_service.controller;

import com.gymapp.workload_service.model.WorkloadRequest;
import com.gymapp.workload_service.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/workload")
public class WorkloadController {

    private final WorkloadService workloadService;

    @PostMapping
    public ResponseEntity<Void> updateWorkLoad(@RequestBody WorkloadRequest request) {
        workloadService.processWorkload(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/{year}/{month}")
    public ResponseEntity<Integer> getWorkLoad(@PathVariable String username,
                                               @PathVariable int year,
                                               @PathVariable int month) {

        Integer hours = workloadService.getHoursForMonth(username, year, month);
        if (hours == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hours);
    }
}
