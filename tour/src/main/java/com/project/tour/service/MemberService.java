// 📁 src/main/java/com/project/tour/service/MemberService.java
package com.project.tour.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tour.dto.MemberRequestDto;
import com.project.tour.entity.Member;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ 회원가입
    @Transactional
    public void signup(MemberRequestDto dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .gender(dto.getGender())
                .birthday(dto.getBirthday())
                .phoneNumber(dto.getPhoneNumber())
                .role(formatRole(dto.getRole())) // 항상 ROLE_ prefix
                .build();

        memberRepository.save(member);
    }

    private String formatRole(String role) {
        return (role != null && role.startsWith("ROLE_")) ? role : "ROLE_" + (role == null ? "USER" : role);
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // ✅ 실제 member의 role을 JWT에 넣음
        return jwtUtil.createToken(member.getEmail(), member.getRole());
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
