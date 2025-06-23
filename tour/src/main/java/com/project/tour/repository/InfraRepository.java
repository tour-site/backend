package com.project.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tour.entity.Food;

@Repository
public interface InfraRepository extends JpaRepository<Food, Long> {
    @Query(value = """
        SELECT DISTINCT tour_city FROM TOUR_INFO
        UNION
        SELECT DISTINCT tour_city FROM FOOD_INFO
        UNION
        SELECT DISTINCT tour_city FROM STAY_INFO
        ORDER BY tour_city
    """, nativeQuery = true)
    List<String> findAllDistricts();
}
