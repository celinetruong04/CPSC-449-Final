package com.surfapi.surfobservationapi.service;

import com.surfapi.surfobservationapi.dto.ObservationRequest;
import com.surfapi.surfobservationapi.dto.ObservationResponse;
import com.surfapi.surfobservationapi.entity.SurfObservation;
import com.surfapi.surfobservationapi.entity.User;
import com.surfapi.surfobservationapi.repository.SurfObservationRepository;
import com.surfapi.surfobservationapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationService {

    private final SurfObservationRepository observationRepository;
    private final UserRepository userRepository;

    // inject repositories
    public ObservationService(SurfObservationRepository observationRepository,
                              UserRepository userRepository) {
        this.observationRepository = observationRepository;
        this.userRepository = userRepository;
    }

    // create a new observation for the authenticated user
    public ObservationResponse createObservation(Long userId,
                                                 ObservationRequest request) {

        // find authenticated user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // build observation entity
        SurfObservation observation = SurfObservation.builder()
                .spotName(request.getSpotName())
                .waveHeight(request.getWaveHeight())
                .windSpeed(request.getWindSpeed())
                .windDirection(request.getWindDirection())
                .tide(request.getTide())
                .rating(request.getRating())
                .notes(request.getNotes())
                .observationDate(request.getObservationDate())
                .user(user)
                .build();

        // save observation
        SurfObservation savedObservation =
                observationRepository.save(observation);

        // return response DTO
        return mapToResponse(savedObservation);
    }

    // get all observations belonging to authenticated user
    public List<ObservationResponse> getUserObservations(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return observationRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // convert entity into response DTO
    private ObservationResponse mapToResponse(SurfObservation observation) {

        return ObservationResponse.builder()
                .id(observation.getId())
                .spotName(observation.getSpotName())
                .waveHeight(observation.getWaveHeight())
                .windSpeed(observation.getWindSpeed())
                .windDirection(observation.getWindDirection())
                .tide(observation.getTide())
                .rating(observation.getRating())
                .notes(observation.getNotes())
                .observationDate(observation.getObservationDate())
                .build();
    }
    // get one observation by id if it belongs to authenticated user
    public ObservationResponse getObservationById(Long userId, Long observationId) {

        SurfObservation observation = observationRepository.findById(observationId)
                .orElseThrow(() -> new RuntimeException("Observation not found"));

        if (!observation.getUser().getId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        return mapToResponse(observation);
    }

    // update an observation if it belongs to authenticated user
    public ObservationResponse updateObservation(Long userId,
                                                 Long observationId,
                                                 ObservationRequest request) {

        SurfObservation observation = observationRepository.findById(observationId)
                .orElseThrow(() -> new RuntimeException("Observation not found"));

        if (!observation.getUser().getId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        observation.setSpotName(request.getSpotName());
        observation.setWaveHeight(request.getWaveHeight());
        observation.setWindSpeed(request.getWindSpeed());
        observation.setWindDirection(request.getWindDirection());
        observation.setTide(request.getTide());
        observation.setRating(request.getRating());
        observation.setNotes(request.getNotes());
        observation.setObservationDate(request.getObservationDate());

        SurfObservation updatedObservation = observationRepository.save(observation);

        return mapToResponse(updatedObservation);
    }

    // delete an observation if it belongs to authenticated user
    public void deleteObservation(Long userId, Long observationId) {

        SurfObservation observation = observationRepository.findById(observationId)
                .orElseThrow(() -> new RuntimeException("Observation not found"));

        if (!observation.getUser().getId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        observationRepository.delete(observation);
    }
}
