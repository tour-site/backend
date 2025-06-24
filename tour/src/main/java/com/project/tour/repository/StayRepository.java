package com.project.tour.repository;

import com.project.tour.entity.Stay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
    Optional<Stay> findById(Long id);
    long countByCity(String city);
    List<Stay> findByCity(String city);
}
