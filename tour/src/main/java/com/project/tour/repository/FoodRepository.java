package com.project.tour.repository;

import com.project.tour.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    long countByCity(String city);
}