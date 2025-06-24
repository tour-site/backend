// 📁 src/main/java/com/project/tour/service/CommentService.java
package com.project.tour.service;

import com.project.tour.dto.CommentRequestDto;
import com.project.tour.entity.Board;
import com.project.tour.entity.BoardComment;
import com.project.tour.repository.BoardRepository;
import com.project.tour.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

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

}
