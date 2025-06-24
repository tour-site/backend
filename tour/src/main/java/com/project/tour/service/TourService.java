package com.project.tour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tour.entity.Tour;
import com.project.tour.repository.TourRepository;

@Service
public class TourService {

    @Autowired
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> getToursByCity(String city) {
        return tourRepository.findByCity(city);
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 관광지를 찾을 수 없습니다: " + id));
    }
}

