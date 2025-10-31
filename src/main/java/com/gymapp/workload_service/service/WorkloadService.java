package com.gymapp.workload_service.service;

import com.gymapp.workload_service.model.TrainerSummary;
import com.gymapp.workload_service.model.WorkloadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WorkloadService {

    private final Logger logger = LoggerFactory.getLogger(WorkloadService.class);
    private final ConcurrentHashMap<String, TrainerSummary> db = new ConcurrentHashMap<>();

    public void processWorkload(WorkloadRequest req) {

        logger.info("Received workload request for trainer [{}] date [{}] duration [{}] action [{}]",
                req.getTrainerUsername(), req.getTrainingDate(), req.getDuration(), req.getActionType());

        TrainerSummary summary = db.computeIfAbsent( // returns the trainer if it exists (by username), otherwise builds it.
                req.getTrainerUsername(),
                key -> new TrainerSummary(req.getTrainerUsername(),
                        req.getTrainerFirstName(),
                        req.getTrainerLastName(),
                        req.isActive())
        );

        LocalDate date = req.getTrainingDate();
        int year = date.getYear();
        int month = date.getMonthValue();

        if ("ADD".equalsIgnoreCase(req.getActionType())) {
            summary.updateHours(year, month, req.getDuration());
        } else if ("DELETE".equalsIgnoreCase(req.getActionType())) {
            summary.updateHours(year, month, -req.getDuration());
        }
    }

    public Integer getHoursForMonth(String username, int year, int month) {
        TrainerSummary summary = db.get(username);
        if (summary == null) return null;
        return summary.getMonthlyHours(year, month);
    }
}














