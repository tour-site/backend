// 📁 src/main/java/com/project/tour/service/MemberService.java
package com.project.tour.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.tour.entity.Member;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveOrGetMember(JsonNode userInfo) {
        JsonNode kakaoAccount = userInfo.get("kakao_account");
        JsonNode profile = kakaoAccount.get("profile");

        String email = getSafeText(kakaoAccount, "email");
        String name = getSafeText(kakaoAccount, "name");
        String gender = getSafeText(kakaoAccount, "gender");
        String ageRange = getSafeText(kakaoAccount, "age_range");
        String phoneNumber = getSafeText(kakaoAccount, "phone_number");

        String nickname = profile != null ? getSafeText(profile, "nickname") : null;
        String profileImage = profile != null ? getSafeText(profile, "profile_image_url") : null;

        String birthday = getSafeText(kakaoAccount, "birthday");
        String birthyear = getSafeText(kakaoAccount, "birthyear");

        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .email(email)
                        .name(name)
                        .gender(gender)
                        .phoneNumber(phoneNumber)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .birthday(birthday)
                        .build()));
    }

    private String getSafeText(JsonNode node, String fieldName) {
        return node.has(fieldName) && !node.get(fieldName).isNull()
                ? node.get(fieldName).asText()
                : null;
    }
}
