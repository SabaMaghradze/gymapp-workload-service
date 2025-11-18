package com.gymapp.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collation = "trainer_summary")
@CompoundIndex(name = "first_last_name_idx", def = "{'trainerFirstname': 1, 'trainerLastname': 1}")
public class TrainerSummary {

    @Id
    private String trainerUsername;

    private String trainerFirstName;

    private String trainerLastName;

    private boolean trainerStatus;

    List<YearSummary> yearSummaries = new ArrayList<>();

    @Data
    public static class YearSummary {
        private int year;
        private List<MonthSummary> monthSummaries = new ArrayList<>();
    }

    @Data
    public static class MonthSummary {
        private int month;
        private int trainerDuration;
    }

    public TrainerSummary(String trainerUsername, String trainerFirstName, String trainerLastName, boolean trainerStatus) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.trainerStatus = trainerStatus;
    }
}
