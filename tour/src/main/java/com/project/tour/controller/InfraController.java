package com.project.tour.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.InfraDTO;
import com.project.tour.service.InfraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/infra")
@RequiredArgsConstructor
public class InfraController {

    private final InfraService service;

    @GetMapping("/count")
    public List<InfraDTO> getInfraCounts(@RequestParam String city) {
        return service.getInfraCounts(city);
    }

    @GetMapping("/districts")
    public List<String> getDistricts() {
        return service.getAllDistricts();
    }
}
