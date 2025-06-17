package com.project.tour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "안녕하세요, 프론트에서 받은 요청입니다!";
    }

    @GetMapping("/bye")
    public String bye() {
        return "집으로 가버렸~~~~";
    }

}
