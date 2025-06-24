// 📁 src/main/java/com/project/tour/dto/BoardLikeResponseDto.java
package com.project.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardLikeResponseDto {
    private boolean liked; // true = 좋아요 완료, false = 좋아요 취소
    private long likeCount; // 현재 게시글 좋아요 수
}
