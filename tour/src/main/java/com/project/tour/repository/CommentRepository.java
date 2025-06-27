// 📁 src/main/java/com/project/tour/repository/CommentRepository.java
package com.project.tour.repository;

import com.project.tour.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByBoardId(Long boardId);

    List<BoardComment> findByWriterIdAndWriterType(Long writerId, String writerType);
}
