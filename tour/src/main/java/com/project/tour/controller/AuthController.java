// 📁 src/main/java/com/project/tour/controller/AuthController.java
package com.project.tour.controller;

import com.project.tour.dto.KakaoUserInfoDto;
import com.project.tour.entity.KakaoMember;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.service.KakaoMemberService;
import com.project.tour.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final KakaoUtil kakaoUtil;
    private final KakaoMemberService kakaoMemberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        // 1️⃣ 카카오 액세스 토큰 가져오기
        String accessToken = kakaoUtil.getAccessToken(code);

        // 2️⃣ 사용자 정보 가져오기
        KakaoUserInfoDto userInfo = kakaoUtil.getUserInfo(accessToken);

        // 3️⃣ DB에 사용자 저장 (최초 로그인 시)
        KakaoMember member = kakaoMemberService.registerIfNotExists(userInfo);

        // 4️⃣ JWT 토큰 생성
        String token = jwtUtil.createToken(member.getEmail(), "ROLE_KAKAO");

        // 5️⃣ 쿠키에 저장
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // JS 접근 차단
        cookie.setMaxAge(60 * 60); // 1시간
        response.addCookie(cookie);

        // 6️⃣ 프론트에 성공 응답
        return ResponseEntity.ok("카카오 로그인 성공");

    }

    // ✅ 로그아웃 처리
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("token", null);
        expiredCookie.setPath("/");
        expiredCookie.setMaxAge(0); // 즉시 만료
        expiredCookie.setHttpOnly(true);
        response.addCookie(expiredCookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
