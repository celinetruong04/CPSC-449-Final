package com.surfapi.surfobservationapi.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ObservationRequest {
    private String spotName;
    private Double waveHeight;
    private Double windSpeed;
    private String windDirection;
    private String tide;
    private Integer rating;
    private String notes;
    private LocalDate observationDate;
}
