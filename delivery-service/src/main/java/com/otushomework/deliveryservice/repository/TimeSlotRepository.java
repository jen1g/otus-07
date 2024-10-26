package com.otushomework.deliveryservice.repository;

import com.otushomework.deliveryservice.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
    Optional<TimeSlot> findByTimeslot(String timeslotId);
}