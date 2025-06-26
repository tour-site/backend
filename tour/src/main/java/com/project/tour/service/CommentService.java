// 📁 src/main/java/com/project/tour/service/CommentService.java
package com.project.tour.service;

import com.project.tour.dto.CommentRequestDto;
import com.project.tour.dto.CommentResponseDto;
import com.project.tour.entity.Board;
import com.project.tour.entity.BoardComment;
import com.project.tour.entity.KakaoMember;
import com.project.tour.entity.Member;
import com.project.tour.repository.BoardRepository;
import com.project.tour.repository.CommentRepository;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;

    public BoardComment addComment(CommentRequestDto dto, Long writerId, String writerType) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        BoardComment comment = BoardComment.builder()
                .board(board)
                .content(dto.getContent())
                .writerId(writerId)
                .writerType(writerType)
                .build();

        return commentRepository.save(comment);
    }

    public List<BoardComment> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public void deleteComment(Long commentId, Long loginMemberId, String role) {
        BoardComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getWriterId().equals(loginMemberId) || !comment.getWriterType().equals(role)) {
            throw new SecurityException("본인만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    // ✅ 마이페이지: 작성한 댓글 목록 조회 (닉네임 포함)
    public List<CommentResponseDto> findByWriter(Long writerId, String writerType) {
        return commentRepository.findByWriterIdAndWriterType(writerId, writerType)
                .stream()
                .map(comment -> {
                    String nickname = getWriterNickname(writerId, writerType);
                    String email = getWriterEmail(writerId, writerType);
                    return CommentResponseDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .writerId(comment.getWriterId())
                            .writerType(comment.getWriterType())
                            .writerNickname(nickname)
                            .email(email)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private String getWriterNickname(Long writerId, String writerType) {
        if ("ROLE_USER".equals(writerType) || "ROLE_ADMIN".equals(writerType)) {
            return memberRepository.findById(writerId)
                    .map(Member::getNickname)
                    .orElse("탈퇴한 회원");
        } else if ("ROLE_KAKAO".equals(writerType)) {
            return kakaoMemberRepository.findById(writerId)
                    .map(KakaoMember::getNickname)
                    .orElse("탈퇴한 회원");
        } else {
            return "알 수 없음";
        }
    }

    private String getWriterEmail(Long writerId, String writerType) {
        if ("ROLE_USER".equals(writerType) || "ROLE_ADMIN".equals(writerType)) {
            return memberRepository.findById(writerId)
                    .map(Member::getEmail)
                    .orElse("");
        } else if ("ROLE_KAKAO".equals(writerType)) {
            return kakaoMemberRepository.findById(writerId)
                    .map(KakaoMember::getEmail)
                    .orElse("");
        } else {
            return "";
        }
    }
}
