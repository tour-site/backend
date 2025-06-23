// 📁 src/main/java/com/project/tour/service/KakaoMemberService.java
package com.project.tour.service;

import com.project.tour.dto.KakaoUserInfoDto;
import com.project.tour.entity.KakaoMember;
import com.project.tour.repository.KakaoMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoMemberService {

    private final KakaoMemberRepository kakaoMemberRepository;

    public KakaoMember registerIfNotExists(KakaoUserInfoDto dto) {
        return kakaoMemberRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> kakaoMemberRepository.save(
                        KakaoMember.builder()
                                .email(dto.getEmail())
                                .name(dto.getName())
                                .nickname(dto.getNickname())
                                .profileImage(dto.getProfileImage())
                                .gender(dto.getGender())
                                .birthday(dto.getBirthday())
                                .phoneNumber(dto.getPhoneNumber())
                                .build()));
    }

    public Optional<KakaoMember> findByEmail(String email) {
        System.out.println("✅ 카카오 사용자 최초 등록: " + email);
        return kakaoMemberRepository.findByEmail(email);
    }

    public Optional<KakaoMember> findByPhoneNumber(String phoneNumber) {
        return kakaoMemberRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<KakaoMember> findByEmailAndPhoneNumber(String email, String phoneNumber) {
        return kakaoMemberRepository.findByEmailAndPhoneNumber(email, phoneNumber);
    }
}
