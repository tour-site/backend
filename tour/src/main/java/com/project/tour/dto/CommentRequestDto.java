// 📁 src/main/java/com/project/tour/dto/CommentRequestDto.java
package com.project.tour.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long boardId;
    private String content;
    private Long writerId; // ✅ 추가
    private String writerType; // ✅ 추가
}
