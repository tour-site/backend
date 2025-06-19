// 📁 src/main/java/com/project/tour/controller/AuthController.java
package com.project.tour.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.tour.entity.Member;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.service.MemberService;
import com.project.tour.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoUtil kakaoUtil;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        // 1. 카카오 토큰 요청
        String accessToken = kakaoUtil.getAccessToken(code);

        // 2. 사용자 정보 요청
        JsonNode userInfo = kakaoUtil.getUserInfo(accessToken);

        // ✅ 콘솔 확인용
        System.out.println("📌 Kakao User Info:");
        System.out.println(userInfo.toPrettyString());

        // 3. 회원 저장 or 조회
        Member member = memberService.saveOrGetMember(userInfo);

        // 4. JWT 발급
        String jwt = jwtUtil.createToken(member.getEmail());

        // 5. 응답 반환
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
