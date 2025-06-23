// 📁 src/main/java/com/project/tour/dto/MemberRequestDto.java
package com.project.tour.dto;

import com.project.tour.entity.KakaoMember;
import com.project.tour.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String email; // 필수
    private String password; // 필수 (회원가입/로그인 시)
    private String name; // 필수
    private String nickname; // 필수
    private String gender; // 선택
    private String birthday; // 선택
    private String phoneNumber; // 선택
    private String profileImage; // 선택

    public MemberRequestDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImage = null; // 일반회원은 기본적으로 없음
    }

    public MemberRequestDto(KakaoMember kakao) {
        this.name = kakao.getName();
        this.email = kakao.getEmail();
        this.nickname = kakao.getNickname();
        this.profileImage = kakao.getProfileImage(); // 카카오는 이게 있음
    }
}
