// 📁 src/main/java/com/project/tour/controller/MemberController.java
package com.project.tour.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.LoginRequestDto;
import com.project.tour.dto.MemberInfoDto;
import com.project.tour.dto.MemberRequestDto;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import com.project.tour.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto, HttpServletResponse response) {
        String token = memberService.login(dto.getEmail(), dto.getPassword());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok("로그인 성공");
    }
    // 📁 src/main/java/com/project/tour/controller/MemberController.java

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyInfo(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패");
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);

        if ("USER".equals(role)) {
            return memberRepository.findByEmail(email)
                    .<ResponseEntity<?>>map(member -> ResponseEntity.ok(
                            new MemberInfoDto(member.getName(), member.getEmail(), member.getNickname(), null)))
                    .orElse(ResponseEntity.status(404).body("사용자 없음"));
        } else if ("KAKAO".equals(role)) {
            return kakaoMemberRepository.findByEmail(email)
                    .<ResponseEntity<?>>map(kakao -> ResponseEntity.ok(
                            new MemberInfoDto(kakao.getName(), kakao.getEmail(), kakao.getNickname(),
                                    kakao.getProfileImage())))
                    .orElse(ResponseEntity.status(404).body("사용자 없음"));
        }

        return ResponseEntity.badRequest().body("역할 오류");
    }

    // 📁 src/main/java/com/project/tour/controller/MemberController.java

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto dto) {
        try {
            memberService.signup(dto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
        }
    }

}
