package com.project.tour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tour.entity.Stay;
import com.project.tour.repository.StayRepository;

@Service
public class StayService {
    @Autowired
    private final StayRepository stayRepository;

    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }

    public List<Stay> getStaysByCity(String city) {
        return stayRepository.findByCity(city);
    }

    public Stay getStayById(Long id) {
        return stayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 관광지를 찾을 수 없습니다: " + id));
    }
}
