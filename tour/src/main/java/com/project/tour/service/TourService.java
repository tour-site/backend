package com.project.tour.service;

import org.springframework.stereotype.Service;

import com.project.tour.entity.Tour;
import com.project.tour.repository.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 관광지를 찾을 수 없습니다."));
    }
}

