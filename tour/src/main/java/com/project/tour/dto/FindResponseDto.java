// 📁 src/main/java/com/project/tour/dto/FindResponseDto.java
package com.project.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindResponseDto {
    private String result; // 이메일 또는 비밀번호 (실제 비밀번호는 ❌, 테스트니까 보여줌)
}
