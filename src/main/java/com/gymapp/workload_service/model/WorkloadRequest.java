package com.gymapp.workload_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkloadRequest implements Serializable {

    @NotBlank(message = "Trainer username is required")
    private String trainerUsername;

    @NotBlank(message = "Trainer first name is required")
    private String trainerFirstName;

    @NotBlank(message = "Trainer last name is required")
    private String trainerLastName;

    private boolean isActive;

    @NotNull(message = "Training date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate trainingDate;

    @NotNull(message = "Duration is required")
    private Integer duration;

    @NotBlank(message = "Action type is required")
    @Pattern(regexp = "ADD|CANCEL", message = "Action type must be either ADD or CANCEL")
    private String actionType;
}
