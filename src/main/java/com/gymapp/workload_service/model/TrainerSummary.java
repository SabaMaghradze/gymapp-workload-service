package com.gymapp.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.*;

@AllArgsConstructor
@Data
@Document(collation = "trainer_summary")
@CompoundIndex(name = "first_last_name_idx", def = "{'trainerFirstname': 1, 'trainerLastname': 1}")
public class TrainerSummary {

    @Id
    private String trainerUsername;

    private String trainerFirstName;

    private String trainerLastName;

    private boolean isActive;

    Set<Map<Integer, Integer>> trainingsDuration = new HashSet<>();

    public TrainerSummary(String trainerUsername, String trainerFirstName, String trainerLastName, boolean isActive) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.isActive = isActive;
    }
}
