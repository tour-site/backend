// 📁 src/main/java/com/project/tour/dto/KakaoUserInfoDto.java
package com.project.tour.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUserInfoDto {

    private String email;
    private String name;
    private String nickname;
    private String profileImage;
    private String gender;
    private String birthday;
    private String phoneNumber;
}
