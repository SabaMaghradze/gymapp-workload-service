package com.gymapp.workload_service.repository;

import com.gymapp.workload_service.model.TrainerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummary, String> {
    Optional<TrainerSummary> findByTrainerUsername(String trainerUsername);
}
