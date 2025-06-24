// 📁 src/main/java/com/project/tour/dto/MemberAdminDto.java
package com.project.tour.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAdminDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String role;
}
