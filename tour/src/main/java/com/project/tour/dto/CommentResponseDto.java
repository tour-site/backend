// 📁 src/main/java/com/project/tour/dto/CommentResponseDto.java
package com.project.tour.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long writerId; // ✅ 추가
    private String writerType; // ✅ 추가
    private String writerNickname; // ✅ 추가
    private String email; // ✅ 추가
}
