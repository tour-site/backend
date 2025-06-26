package com.project.tour.repository;

import java.util.List;
import org.springframework.lang.NonNull;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Override
    @NonNull
    Optional<Food> findById(@NonNull Long id);

    long countByCity(String city);

    List<Food> findByCity(String city);
}