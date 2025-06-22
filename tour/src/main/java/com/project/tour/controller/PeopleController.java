package com.project.tour.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.PeopleDTO;
import com.project.tour.service.PeopleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PeopleService peopleService;

    @GetMapping("/stats")
    public List<PeopleDTO> getStats(@RequestParam int year, @RequestParam int month) {
        System.out.println("요청됨 year=" + year + ", month=" + month);
        return peopleService.getStats(year, month);
    }

}
