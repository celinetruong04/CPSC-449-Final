package com.surfapi.surfobservationapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ObservationRequest {
    @NotBlank(message = "Spot name is required.")
    private String spotName;
    private Double waveHeight;
    private Double windSpeed;
    private String windDirection;
    private String tide;
    private Integer rating;
    private String notes;
    private LocalDate observationDate;
}
