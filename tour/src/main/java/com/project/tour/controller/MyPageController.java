package com.project.tour.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour.dto.BoardResponseDto;
import com.project.tour.dto.CommentResponseDto;
import com.project.tour.entity.Member;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import com.project.tour.service.BoardService;
import com.project.tour.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<?> getMyComments(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패: 유효하지 않은 토큰");
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token); // ROLE_USER, ROLE_KAKAO 등
        Long writerId;

        try {
            if ("ROLE_USER".equals(role)) {
                writerId = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다.")).getId();
            } else if ("ROLE_KAKAO".equals(role)) {
                writerId = kakaoMemberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("카카오 회원 정보가 없습니다.")).getId();
            } else {
                return ResponseEntity.badRequest().body("권한 오류: 지원하지 않는 ROLE");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("인증 실패: " + e.getMessage());
        }

        // 본인 댓글만 반환
        List<CommentResponseDto> comments = commentService.findByWriter(writerId, role);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getMyBoards(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패: 유효하지 않은 토큰");
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token); // ROLE_USER, ROLE_KAKAO 등
        Long writerId;

        try {
            if ("ROLE_USER".equals(role) || "ROLE_ADMIN".equals(role)) {
                writerId = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다.")).getId();
            } else if ("ROLE_KAKAO".equals(role)) {
                writerId = kakaoMemberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("카카오 회원 정보가 없습니다.")).getId();
            } else {
                return ResponseEntity.badRequest().body("권한 오류: 지원하지 않는 ROLE");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("인증 실패: " + e.getMessage());
        }

        // 본인 게시글만 반환
        List<BoardResponseDto> boards = boardService.findByWriter(writerId, role);
        return ResponseEntity.ok(boards);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패: 유효하지 않은 토큰");
        }
        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        String newNickname = body.get("nickname");
        if (newNickname == null || newNickname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("닉네임을 입력하세요");
        }
        try {
            if ("ROLE_USER".equals(role) || "ROLE_ADMIN".equals(role)) {
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
                member.setNickname(newNickname);
                memberRepository.save(member);
            } else {
                return ResponseEntity.badRequest().body("카카오 회원은 닉네임을 수정할 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("닉네임 변경 실패: " + e.getMessage());
        }
        return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다.");
    }
}
