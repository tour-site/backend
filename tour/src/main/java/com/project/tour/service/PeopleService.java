package com.project.tour.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour.dto.PeopleDTO;
import com.project.tour.repository.PeopleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public List<PeopleDTO> getStats(int year, int month) {
        return peopleRepository.getPeopleStatsByYearMonth(year, month);
    }
}
