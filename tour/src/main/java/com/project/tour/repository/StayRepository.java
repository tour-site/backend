package com.project.tour.repository;

import com.project.tour.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
    long countByCity(String city);
}
