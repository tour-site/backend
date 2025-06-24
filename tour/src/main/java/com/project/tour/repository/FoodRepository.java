package com.project.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findById(Long id);
    long countByCity(String city);
    List<Food> findByCity(String city);
}