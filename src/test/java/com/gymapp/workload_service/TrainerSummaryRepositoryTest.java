package com.gymapp.workload_service;

import com.gymapp.workload_service.model.TrainerSummary;
import com.gymapp.workload_service.repository.TrainerSummaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class TrainerSummaryRepositoryTest {

    @Autowired
    private TrainerSummaryRepository repository;

    @Test
    void testSaveAndFindByUsername() {
        TrainerSummary doc = new TrainerSummary();
        doc.setTrainerUsername("john.doe");
        doc.setTrainerFirstName("John");
        doc.setTrainerLastName("Doe");
        doc.setTrainerStatus(true);
        repository.save(doc);

        var found = repository.findByTrainerUsername("john.doe");
        assertThat(found).isPresent();
        assertThat(found.get().getTrainerFirstName()).isEqualTo("John");
    }
}
