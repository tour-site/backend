package com.project.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.FoodDTO;
import com.project.tour.entity.Food;
import com.project.tour.service.FoodService;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/{id}")
    public FoodDTO getFoodById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        return new FoodDTO(food);
    }

    @GetMapping
    public List<Food> getFoodsByCity(@RequestParam String city) {
        return foodService.getFoodsByCity(city);
    }
}
