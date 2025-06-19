// 📁 src/main/java/com/project/tour/controller/MemberController.java
package com.project.tour.controller;

import com.project.tour.entity.Member;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    // JWT 인증된 사용자 정보 가져오기
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        return ResponseEntity.ok(member);
    }
}
