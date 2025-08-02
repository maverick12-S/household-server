package com.example.household_server.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.household_server.domain.model.NotificationReservation;

@Repository
public interface  NotificationReservationRepository extends JpaRepository<NotificationReservation, Long> {
    
    Optional<NotificationReservation> findByEmail(String email);

    List<NotificationReservation> findByReservedTrue();



}
