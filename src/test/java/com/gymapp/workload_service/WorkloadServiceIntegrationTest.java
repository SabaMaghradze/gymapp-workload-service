package com.gymapp.workload_service;

import com.gymapp.common.WorkloadRequest;
import com.gymapp.workload_service.model.TrainerSummary;
import com.gymapp.workload_service.repository.TrainerSummaryRepository;
import com.gymapp.workload_service.service.WorkloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkloadServiceIntegrationTest {

    private WorkloadService workloadService;

    private TrainerSummaryRepository repository;

    @BeforeEach
    void clearDb() {
        repository.deleteAll();
    }

    @Test
    void testProcessWorkloadCreatesNewTrainer() {
        WorkloadRequest req = new WorkloadRequest();
        req.setTrainerUsername("jane.smith");
        req.setTrainerFirstName("Jane");
        req.setTrainerLastName("Smith");
        req.setActive(true);
        req.setTrainingDate(LocalDate.of(2025, 11, 18));
        req.setDuration(60);
        req.setActionType("ADD");

        workloadService.processWorkload(req, "txn-001");

        TrainerSummary doc = repository.findByTrainerUsername("jane.smith").orElseThrow();
        assertThat(doc.getTrainerFirstName()).isEqualTo("Jane");
        assertThat(doc.getYearSummaries().get(0).getMonthSummaries().get(0).getTrainerDuration()).isEqualTo(60);
    }

    @Test
    void testProcessWorkloadUpdatesExistingTrainer() {

        // First request creates
        WorkloadRequest req1 = new WorkloadRequest();
        req1.setTrainerUsername("jane.smith");
        req1.setTrainerFirstName("Jane");
        req1.setTrainerLastName("Smith");
        req1.setActive(true);
        req1.setTrainingDate(LocalDate.of(2025, 11, 18));
        req1.setDuration(60);
        req1.setActionType("ADD");

        workloadService.processWorkload(req1, "txn-002");

        // Second request updates same year/month
        WorkloadRequest req2 = new WorkloadRequest();
        req2.setTrainerUsername("jane.smith");
        req2.setTrainerFirstName("Jane");
        req2.setTrainerLastName("Smith");
        req2.setActive(true);
        req2.setTrainingDate(LocalDate.of(2025, 11, 18));
        req2.setDuration(30);
        req2.setActionType("ADD");

        workloadService.processWorkload(req2, "txn-003");

        TrainerSummary doc = repository.findByTrainerUsername("jane.smith").orElseThrow();
        assertThat(doc.getYearSummaries().get(0).getMonthSummaries().get(0).getTrainerDuration())
                .isEqualTo(90); // 60 + 30
    }
}
