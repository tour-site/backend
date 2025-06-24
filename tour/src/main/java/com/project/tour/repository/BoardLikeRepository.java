package com.project.tour.repository;

import com.project.tour.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    Optional<BoardLike> findByBoardIdAndWriterIdAndWriterType(Long boardId, Long writerId, String writerType);

    boolean existsByBoardIdAndWriterIdAndWriterType(Long boardId, Long writerId, String writerType);

    void deleteByBoardIdAndWriterIdAndWriterType(Long boardId, Long writerId, String writerType);

    long countByBoardId(Long boardId);
}
