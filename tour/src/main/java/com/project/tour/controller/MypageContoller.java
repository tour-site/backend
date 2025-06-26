package com.project.tour.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.BoardResponseDto;
import com.project.tour.dto.CommentResponseDto;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import com.project.tour.service.BoardService;
import com.project.tour.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

public class MypageContoller {

    // 📁 src/main/java/com/project/tour/controller/MyPageController.java
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/mypage")
    public class MyPageController {

        private final JwtUtil jwtUtil;
        private final MemberRepository memberRepository;
        private final KakaoMemberRepository kakaoMemberRepository;
        private final BoardService boardService;
        private final CommentService commentService;

        @GetMapping
        public ResponseEntity<?> getMyPage(HttpServletRequest request) {
            String token = jwtUtil.resolveToken(request);
            if (token == null || !jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("인증 실패");
            }

            String email = jwtUtil.getUserEmail(token);
            String role = jwtUtil.getUserRole(token); // "USER" or "KAKAO"
            Long writerId;

            if ("ROLE_USER".equals(role)) {
                writerId = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("회원 없음")).getId();
            } else if ("ROLE_KAKAO".equals(role)) {
                writerId = kakaoMemberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("카카오 회원 없음")).getId();
            } else {
                return ResponseEntity.badRequest().body("권한 오류");
            }

            // ✅ 게시글/댓글 조회
            List<BoardResponseDto> boards = boardService.findByWriter(writerId, role);
            List<CommentResponseDto> comments = commentService.findByWriter(writerId, role);

            return ResponseEntity.ok(Map.of(
                    "boards", boards,
                    "comments", comments));
        }
    }

}
