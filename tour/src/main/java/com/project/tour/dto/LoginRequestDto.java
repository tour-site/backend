// 📁 src/main/java/com/project/tour/dto/LoginRequestDto.java
package com.project.tour.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
