package com.surfapi.surfobservationapi.repository;

import com.surfapi.surfobservationapi.entity.SurfObservation;
import com.surfapi.surfobservationapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurfObservationRepository extends JpaRepository<SurfObservation, Long> {

    List<SurfObservation> findByUser(User user);
}