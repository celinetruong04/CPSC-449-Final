package com.surfapi.surfobservationapi.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ObservationResponse {
    private Long id;
    private String spotName;
    private Double waveHeight;
    private Double windSpeed;
    private String windDirection;
    private String tide;
    private Integer rating;
    private String notes;
    private LocalDate observationDate;
}