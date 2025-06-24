package com.project.tour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tour.entity.Food;
import com.project.tour.repository.FoodRepository;

@Service
public class FoodService {
    
    @Autowired
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getFoodsByCity(String city) {
        return foodRepository.findByCity(city);
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 관광지를 찾을 수 없습니다: " + id));
    }
}
