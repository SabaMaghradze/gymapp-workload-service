package com.gymapp.workload_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Data
public class TrainerSummary {

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;

    public TrainerSummary(String trainerUsername, String trainerFirstName, String trainerLastName, boolean isActive) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.isActive = isActive;
    }

    private Map<Integer, Map<Integer, Integer>> yearlyData = new HashMap<>();

    public void updateHours(int year, int month, Integer hours) {
        yearlyData.putIfAbsent(year, new HashMap<>());
        yearlyData.get(year) // gets the existing map of months for that year
                .put(month, yearlyData.get(year).getOrDefault(month, 0) + hours);

//        Map<Integer, Integer> monthMap = yearlyData.get(year);
//        if (monthMap == null) {
//            monthMap = new HashMap<>();
//            yearlyData.put(year, monthMap);
//        }
//        int currentHours = monthMap.getOrDefault(month, 0);
//        monthMap.put(month, currentHours + hours);
    }

    public int getMonthlyHours(int year, int month) {
        return yearlyData.getOrDefault(year, new HashMap<>()).getOrDefault(month, 0);
    }
}
