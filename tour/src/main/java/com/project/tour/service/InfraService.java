package com.project.tour.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour.dto.InfraDTO;
import com.project.tour.repository.FoodRepository;
import com.project.tour.repository.InfraRepository;
import com.project.tour.repository.StayRepository;
import com.project.tour.repository.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfraService {

    private final FoodRepository foodRepo;
    private final StayRepository stayRepo;
    private final TourRepository tourRepo;
    private final InfraRepository infraRepository;

    public List<InfraDTO> getInfraCounts(String city) {
        long foodCount = foodRepo.countByCity(city);
        long stayCount = stayRepo.countByCity(city);
        long tourCount = tourRepo.countByCity(city);

        return List.of(
            new InfraDTO("음식점", foodCount),
            new InfraDTO("숙소", stayCount),
            new InfraDTO("관광지", tourCount)
        );
    }

    public List<String> getAllDistricts() {
        return infraRepository.findAllDistricts();
    }
}
