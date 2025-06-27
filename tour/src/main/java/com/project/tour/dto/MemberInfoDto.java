// 📁 src/main/java/com/project/tour/dto/MemberInfoDto.java
package com.project.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String name;
    private String email;
    private String nickname;
    private String profileImage; // ✅ 일반 회원은 null, 카카오 회원은 URL
    private String role; // ✅ 추가
}
