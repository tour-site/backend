// 📁 src/main/java/com/project/tour/jwt/JwtAuthenticationFilter.java
package com.project.tour.jwt;

import com.project.tour.entity.KakaoMember;
import com.project.tour.entity.Member;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);
        System.out.println("[JwtFilter] 🔍 추출된 토큰: " + token);

        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.getUserEmail(token);
            String role = jwtUtil.getUserRole(token);
            System.out.println("[JwtFilter] 📨 토큰 이메일: " + email);
            System.out.println("[JwtFilter] 🛡️ 토큰 권한: " + role);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            if ("ROLE_USER".equals(role) || "ROLE_ADMIN".equals(role)) {
                memberRepository.findByEmail(email).ifPresentOrElse(member -> {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            member.getEmail(), null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("[JwtFilter] ✅ 일반 회원 인증 완료: " + member.getEmail());
                }, () -> {
                    System.out.println("[JwtFilter] ⚠️ 일반 회원 정보 없음: " + email);
                });

            } else if ("ROLE_KAKAO".equals(role)) {
                kakaoMemberRepository.findByEmail(email).ifPresentOrElse(kakao -> {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(kakao.getEmail(),
                            null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("[JwtFilter] ✅ 카카오 회원 인증 완료: " + kakao.getEmail());
                }, () -> {
                    System.out.println("[JwtFilter] ⚠️ 카카오 회원 정보 없음: " + email);
                });

            } else {
                System.out.println("[JwtFilter] ❌ 알 수 없는 권한: " + role);
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("[JwtFilter] ❌ 인증 컨텍스트 등록 실패");
            } else {
                System.out.println("[JwtFilter] ✅ 현재 인증 객체: " +
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }

        } else {
            System.out.println("[JwtFilter] ❌ 토큰 없음 또는 유효하지 않음");
        }

        filterChain.doFilter(request, response);
    }
}
