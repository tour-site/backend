package com.project.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.TourDTO;
import com.project.tour.entity.Tour;
import com.project.tour.service.TourService;

@RestController
@RequestMapping("/api/place")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/{id}")
    public TourDTO getTourById(@PathVariable Long id) {
        Tour tour = tourService.getTourById(id);
        return new TourDTO(tour);
    }

    @GetMapping
    public List<Tour> getToursByCity(@RequestParam String city) {
        return tourService.getToursByCity(city);
    }
}
