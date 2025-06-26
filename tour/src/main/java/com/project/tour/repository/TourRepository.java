package com.project.tour.repository;

import java.util.List;
// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour.entity.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {
    // Optional<Tour> findById(Long id);
    long countByCity(String city);

    List<Tour> findByCity(String city);
}
