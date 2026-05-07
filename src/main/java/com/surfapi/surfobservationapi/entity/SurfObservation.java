package com.surfapi.surfobservationapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "surf_observations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurfObservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String spotName;

    private Double waveHeight;

    private Double windSpeed;

    private String windDirection;

    private String tide;

    private Integer rating;

    private String notes;

    private LocalDate observationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}