package com.project.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByWriterIdAndWriterType(Long writerId, String writerType);
}
