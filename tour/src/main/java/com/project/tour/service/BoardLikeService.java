// 📁 src/main/java/com/project/tour/service/BoardLikeService.java
package com.project.tour.service;

import com.project.tour.dto.BoardLikeRequestDto;
import com.project.tour.entity.Board;
import com.project.tour.entity.BoardLike;
import com.project.tour.repository.BoardLikeRepository;
import com.project.tour.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    // 📌 좋아요 토글
    public boolean toggleLike(BoardLikeRequestDto dto, Long writerId, String writerType) {
        Optional<BoardLike> existing = boardLikeRepository
                .findByBoardIdAndWriterIdAndWriterType(dto.getBoardId(), writerId, writerType);

        if (existing.isPresent()) {
            boardLikeRepository.delete(existing.get()); // 이미 누른 경우 → 취소
            return false;
        }

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        BoardLike like = BoardLike.builder()
                .board(board)
                .writerId(writerId)
                .writerType(writerType)
                .build();
        boardLikeRepository.save(like);
        return true;
    }

    // 📌 좋아요 개수 조회
    public long countLikes(Long boardId) {
        return boardLikeRepository.countByBoardId(boardId);
    }
}
