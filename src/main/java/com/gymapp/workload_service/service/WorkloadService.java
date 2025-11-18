package com.gymapp.workload_service.service;

import com.gymapp.common.WorkloadRequest;
import com.gymapp.workload_service.model.TrainerSummary;
import com.gymapp.workload_service.repository.TrainerSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class WorkloadService {

    private static final Logger opLogger = LoggerFactory.getLogger(WorkloadService.class);
    private static final Logger trLogger = LoggerFactory.getLogger("transaction");

    private final TrainerSummaryRepository repository;

    public void processWorkload(WorkloadRequest req, String transactionId) {

        trLogger.info("[{}] Processing workload for trainer {}", transactionId, req.getTrainerUsername());

        LocalDate date = req.getTrainingDate();
        int year = date.getYear();
        int month = date.getMonthValue();

        TrainerSummary trainerDoc = repository.findByTrainerUsername(req.getTrainerUsername())
                .orElseGet(() -> {
                    opLogger.info("[{}] Creating new trainer document", transactionId);
                    TrainerSummary newDoc = new TrainerSummary();
                    newDoc.setTrainerUsername(req.getTrainerUsername());
                    newDoc.setTrainerFirstName(req.getTrainerFirstName());
                    newDoc.setTrainerLastName(req.getTrainerLastName());
                    newDoc.setTrainerStatus(req.isActive());
                    TrainerSummary.YearSummary yearSummary = new TrainerSummary.YearSummary();
                    yearSummary.setYear(year);
                    TrainerSummary.MonthSummary monthSummary = new TrainerSummary.MonthSummary();
                    monthSummary.setMonth(month);
                    monthSummary.setTrainerDuration(req.getDuration());
                    yearSummary.getMonthSummaries().add(monthSummary);
                    newDoc.getYearSummaries().add(yearSummary);
                    return newDoc;
                });

        TrainerSummary.YearSummary yearSummary = trainerDoc.getYearSummaries().stream()
                .filter(y -> y.getYear() == year)
                .findFirst()
                .orElseGet(() -> {
                    TrainerSummary.YearSummary ys = new TrainerSummary.YearSummary();
                    ys.setYear(year);
                    trainerDoc.getYearSummaries().add(ys);
                    return ys;
                });

        TrainerSummary.MonthSummary monthSummary = yearSummary.getMonthSummaries().stream()
                .filter(m -> m.getMonth() == month)
                .findFirst()
                .orElseGet(() -> {
                    TrainerSummary.MonthSummary ms = new TrainerSummary.MonthSummary();
                    ms.setMonth(month);
                    ms.setTrainerDuration(0);
                    yearSummary.getMonthSummaries().add(ms);
                    return ms;
                });

        monthSummary.setTrainerDuration(
                monthSummary.getTrainerDuration() + req.getDuration()
        );

        opLogger.info("[{}] Updated duration for {}-{} to {}", transactionId, year, month, monthSummary.getTrainerDuration());

        repository.save(trainerDoc);
    }
}














