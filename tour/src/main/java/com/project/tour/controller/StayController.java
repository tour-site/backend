package com.project.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.StayDTO;
import com.project.tour.entity.Stay;
import com.project.tour.service.StayService;

@RestController
@RequestMapping("/api/stays")
public class StayController {
    @Autowired
    private StayService stayService;

    @GetMapping("/{id}")
    public StayDTO getStayById(@PathVariable Long id) {
        Stay stay = stayService.getStayById(id);
        return new StayDTO(stay);
    }

    @GetMapping
    public List<Stay> getStaysByCity(@RequestParam String city) {
        return stayService.getStaysByCity(city);
    }
}
