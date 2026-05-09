package com.surfapi.surfobservationapi.controller;

import com.surfapi.surfobservationapi.dto.ObservationRequest;
import com.surfapi.surfobservationapi.dto.ObservationResponse;
import com.surfapi.surfobservationapi.service.ObservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    private final ObservationService observationService;

    // inject observation service
    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    // create a new observation for authenticated user
    @PostMapping
    public ResponseEntity<ObservationResponse> createObservation(
            @Valid @RequestBody ObservationRequest request,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        ObservationResponse response =
                observationService.createObservation(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // get all observations belonging to authenticated user
    @GetMapping
    public ResponseEntity<List<ObservationResponse>> getUserObservations(
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        List<ObservationResponse> observations =
                observationService.getUserObservations(userId);

        return ResponseEntity.ok(observations);
    }
    // get one observation by id for authenticated user
    @GetMapping("/{id}")
    public ResponseEntity<ObservationResponse> getObservationById(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        ObservationResponse response =
                observationService.getObservationById(userId, id);

        return ResponseEntity.ok(response);
    }

    // update an observation for authenticated user
    @PutMapping("/{id}")
    public ResponseEntity<ObservationResponse> updateObservation(
            @PathVariable Long id,
            @Valid @RequestBody ObservationRequest request,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        ObservationResponse response =
                observationService.updateObservation(userId, id, request);

        return ResponseEntity.ok(response);
    }

    // delete an observation for authenticated user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObservation(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        observationService.deleteObservation(userId, id);

        return ResponseEntity.noContent().build();
    }
}