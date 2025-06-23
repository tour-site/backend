package com.project.tour.service;

import com.project.tour.entity.KakaoMember;
import com.project.tour.repository.KakaoMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final KakaoMemberRepository kakaoMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        KakaoMember member = kakaoMemberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        return User.builder()
                .username(member.getEmail())
                .password("") // 비밀번호는 카카오 로그인에서는 사용하지 않음
                .roles("KAKAO") // 권한 설정 (필요시 DB에서 받아오거나 고정)
                .build();
    }
}
