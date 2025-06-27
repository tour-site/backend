// 📁 src/main/java/com/project/tour/service/BoardService.java
package com.project.tour.service;

import com.project.tour.dto.BoardRequestDto;
import com.project.tour.dto.BoardResponseDto;
import com.project.tour.entity.Board;
import com.project.tour.entity.KakaoMember;
import com.project.tour.entity.Member;
import com.project.tour.repository.BoardRepository;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(BoardRequestDto dto, Long writerId, String writerType) {
        return boardRepository.save(Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writerId(writerId)
                .writerType(writerType)
                .build());
    }

    public Board updateBoard(Long id, BoardRequestDto dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardResponseDto> findByWriter(Long writerId, String writerType) {
        return boardRepository.findByWriterIdAndWriterType(writerId, writerType)
                .stream()
                .map(board -> {
                    String nickname = getWriterNickname(board.getWriterId(), board.getWriterType());
                    String email = getWriterEmail(board.getWriterId(), board.getWriterType());
                    return BoardResponseDto.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .createdAt(board.getCreatedAt())
                            .updatedAt(board.getUpdatedAt())
                            .writerId(board.getWriterId())
                            .writerType(board.getWriterType())
                            .writerNickname(nickname)
                            .email(email)
                            .likeCount(board.getLikes().size())
                            .commentCount(board.getComments().size())
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
