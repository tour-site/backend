// 📁 src/main/java/com/project/tour/service/BoardService.java
package com.project.tour.service;

import com.project.tour.dto.BoardRequestDto;
import com.project.tour.entity.Board;
import com.project.tour.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

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
}
