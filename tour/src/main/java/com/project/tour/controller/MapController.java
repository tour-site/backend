package com.project.tour.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.service.KakaoService;

@RestController
@RequestMapping("/api/map")
public class MapController {

    private final KakaoService kakaoService;

    // 생성자 주입
    public MapController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping
    public Map<String, Object> getPlaces(@RequestParam String category, @RequestParam(defaultValue = "1") int page) {
        return kakaoService.getPlacesByCategory(category, page);
    }

    @GetMapping("/ping")
    public String ping() {
        return "백엔드 연결 OK";
    }
}
