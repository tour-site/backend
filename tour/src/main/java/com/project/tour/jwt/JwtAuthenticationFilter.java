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

        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.getUserEmail(token);
            String role = jwtUtil.getUserRole(token); // ex: ROLE_USER, ROLE_ADMIN, ROLE_KAKAO

            // ✅ 권한 부여 객체 생성
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            // ✅ 일반 회원 또는 관리자
            if ("ROLE_USER".equals(role) || "ROLE_ADMIN".equals(role)) {
                memberRepository.findByEmail(email).ifPresent(member -> {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            member.getEmail(), null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });

                // ✅ 카카오 회원
            } else if ("ROLE_KAKAO".equals(role)) {
                kakaoMemberRepository.findByEmail(email).ifPresent(kakao -> {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(kakao.getEmail(),
                            null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            }
            System.out.println("해당 로그인 한사람의 권한 : " + role);
        }

        filterChain.doFilter(request, response);

    }
}
