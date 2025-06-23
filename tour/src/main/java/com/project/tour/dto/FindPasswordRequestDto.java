// 📁 src/main/java/com/project/tour/dto/FindPasswordRequestDto.java
package com.project.tour.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequestDto {
    private String email;
    private String phoneNumber;
}
