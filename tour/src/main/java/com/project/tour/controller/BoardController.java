// 📁 src/main/java/com/project/tour/controller/BoardController.java
package com.project.tour.controller;

import com.project.tour.dto.*;
import com.project.tour.entity.Board;
import com.project.tour.entity.BoardComment;
import com.project.tour.entity.KakaoMember;
import com.project.tour.entity.Member;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import com.project.tour.service.BoardLikeService;
import com.project.tour.service.BoardService;
import com.project.tour.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final BoardLikeService boardLikeService;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;
    private final JwtUtil jwtUtil;

    // 📌 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        List<BoardResponseDto> result = boardService.findAll().stream().map(board -> {
            String nickname = getWriterNickname(board.getWriterId(), board.getWriterType());
            String email = getWriterEmail(board.getWriterId(), board.getWriterType());
            return BoardResponseDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .likeCount(board.getLikes().size())
                    .commentCount(board.getComments().size())
                    .writerId(board.getWriterId())
                    .writerType(board.getWriterType())
                    .writerNickname(nickname)
                    .email(email)
                    .build();
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // 📌 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        Board board = boardService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        String nickname = getWriterNickname(board.getWriterId(), board.getWriterType());
        String email = getWriterEmail(board.getWriterId(), board.getWriterType());
        return ResponseEntity.ok(BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .likeCount(board.getLikes().size())
                .commentCount(board.getComments().size())
                .writerId(board.getWriterId())
                .writerType(board.getWriterType())
                .writerNickname(nickname)
                .email(email)
                .build());
    }

    // 📌 게시글 작성
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto dto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패");
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        Long writerId;

        if ("USER".equals(role)) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            writerId = member.getId();
        } else {
            KakaoMember kakao = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            writerId = kakao.getId();
        }

        Board saved = boardService.createBoard(dto, writerId, role);
        return ResponseEntity.ok(saved);
    }

    // 📌 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto dto,
            HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패");
        }
        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        Long loginId;
        if ("USER".equals(role)) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            loginId = member.getId();
        } else if ("KAKAO".equals(role)) {
            KakaoMember kakao = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            loginId = kakao.getId();
        } else {
            return ResponseEntity.status(403).body("권한 없음");
        }
        Board board = boardService.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if (!loginId.equals(board.getWriterId()) || !role.equals(board.getWriterType())) {
            return ResponseEntity.status(403).body("본인만 수정할 수 있습니다.");
        }
        return ResponseEntity.ok(boardService.updateBoard(id, dto));
    }

    // 📌 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("인증 실패");
        }
        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        Long loginId;
        if ("USER".equals(role)) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            loginId = member.getId();
        } else if ("KAKAO".equals(role)) {
            KakaoMember kakao = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
            loginId = kakao.getId();
        } else {
            return ResponseEntity.status(403).body("권한 없음");
        }
        Board board = boardService.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        if (!loginId.equals(board.getWriterId()) || !role.equals(board.getWriterType())) {
            return ResponseEntity.status(403).body("본인만 삭제할 수 있습니다.");
        }
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }

    // 📌 댓글 작성 (작성자 정보 포함)
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request) {

        dto.setBoardId(id);

        // JWT 토큰에서 사용자 정보 추출
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        Long writerId;
        String nickname;

        if ("USER".equals(role)) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
            writerId = member.getId();
            nickname = member.getNickname();
        } else if ("KAKAO".equals(role)) {
            KakaoMember kakao = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
            writerId = kakao.getId();
            nickname = kakao.getNickname();
        } else {
            return ResponseEntity.badRequest().build();
        }

        // ✅ writerId, writerType은 따로 넘겨줌
        BoardComment comment = commentService.addComment(dto, writerId, role);

        return ResponseEntity.ok(CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .writerId(writerId)
                .writerType(role)
                .writerNickname(nickname)
                .build());
    }

    // 📌 댓글 목록 조회
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long id) {
        List<CommentResponseDto> result = commentService.getCommentsByBoardId(id).stream()
                .map(c -> CommentResponseDto.builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .createdAt(c.getCreatedAt())
                        .writerId(c.getWriterId())
                        .writerType(c.getWriterType())
                        .writerNickname(getWriterNickname(c.getWriterId(), c.getWriterType()))
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // 📌 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token);
        Long writerId;

        if ("USER".equals(role)) {
            writerId = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 없음")).getId();
        } else if ("KAKAO".equals(role)) {
            writerId = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("카카오 사용자 없음")).getId();
        } else {
            return ResponseEntity.badRequest().build();
        }

        commentService.deleteComment(commentId, writerId, role);
        return ResponseEntity.ok().build();
    }

    // 📌 좋아요 토글 (일반 + 카카오 회원 지원)
    @PostMapping("/{id}/like")
    public ResponseEntity<BoardLikeResponseDto> toggleLike(
            @PathVariable Long id,
            HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body(null);
        }

        String email = jwtUtil.getUserEmail(token);
        String role = jwtUtil.getUserRole(token); // "USER" 또는 "KAKAO"
        Long writerId;

        if ("USER".equals(role)) {
            writerId = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자 없음")).getId();
        } else if ("KAKAO".equals(role)) {
            writerId = kakaoMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("카카오 사용자 없음")).getId();
        } else {
            return ResponseEntity.badRequest().build();
        }

        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setBoardId(id);

        boolean liked = boardLikeService.toggleLike(dto, writerId, role);
        long likeCount = boardLikeService.countLikes(id);

        return ResponseEntity.ok(new BoardLikeResponseDto(liked, likeCount));
    }

    // 📌 좋아요 개수 조회
    @GetMapping("/{id}/likes/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long id) {
        return ResponseEntity.ok(boardLikeService.countLikes(id));
    }

    // 📌 작성자 닉네임/이메일 조회 유틸
    private String getWriterNickname(Long writerId, String writerType) {
        if ("USER".equals(writerType)) {
            return memberRepository.findById(writerId).map(Member::getNickname).orElse("탈퇴한 회원");
        } else if ("KAKAO".equals(writerType)) {
            return kakaoMemberRepository.findById(writerId).map(KakaoMember::getNickname).orElse("탈퇴한 회원");
        } else {
            return "알 수 없음";
        }
    }

    private String getWriterEmail(Long writerId, String writerType) {
        if ("USER".equals(writerType)) {
            return memberRepository.findById(writerId).map(Member::getEmail).orElse("");
        } else if ("KAKAO".equals(writerType)) {
            return kakaoMemberRepository.findById(writerId).map(KakaoMember::getEmail).orElse("");
        } else {
            return "";
        }
    }
}
