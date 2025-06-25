package com.project.tour.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 📁 src/main/java/com/project/tour/dto/BoardResponseDto.java
@Getter
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
    private int commentCount;

    private Long writerId;
    private String writerType;
    private String writerNickname;
    private String email;
}
