package com.gymapp.workload_service.controller;

import com.gymapp.common.WorkloadRequest;
import com.gymapp.workload_service.service.WorkloadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/workload")
public class WorkloadController {

    private final WorkloadService workloadService;

    @PostMapping
    public ResponseEntity<Void> updateWorkLoad(@Valid @RequestBody WorkloadRequest request) {
        String transactionId = UUID.randomUUID().toString();
        workloadService.processWorkload(request, transactionId);
        return ResponseEntity.ok().build();
    }
//
//    @GetMapping("/{username}/{year}/{month}")
//    public ResponseEntity<Integer> getWorkLoad(@PathVariable String username,
//                                               @PathVariable int year,
//                                               @PathVariable int month) {
//
//        Integer hours = workloadService.getHoursForMonth(username, year, month);
//        if (hours == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(hours);
//    }
}
