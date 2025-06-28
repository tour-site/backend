// 📁 src/main/java/com/project/tour/config/SecurityConfig.java
package com.project.tour.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.tour.jwt.JwtAuthenticationFilter;
import com.project.tour.jwt.JwtUtil;
import com.project.tour.repository.KakaoMemberRepository;
import com.project.tour.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final KakaoMemberRepository kakaoMemberRepository;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, memberRepository, kakaoMemberRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // 🔐 인증 필요한 요청
                .requestMatchers(HttpMethod.PATCH, "/api/mypage/nickname").hasAnyRole("USER", "ADMIN")

                // ✅ 아래는 비인증 접근 허용 경로들
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/member/**").permitAll()
                .requestMatchers("/api/place/**").permitAll()
                .requestMatchers("/api/people/**").permitAll()
                .requestMatchers("/api/infra/**").permitAll()
                .requestMatchers("/api/foods/**").permitAll()
                .requestMatchers("/api/stays/**").permitAll()
                .requestMatchers("/api/image-gallery/**").permitAll()
                .requestMatchers("/api/board/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()

                // 🔒 어드민 전용
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // 그 외는 인증 필요
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("http://localhost:8321")); // 프론트엔드 주소
        // config.setAllowedOriginPatterns(List.of("http://localhost:5173")); // 프론트엔드
        // 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        config.setExposedHeaders(List.of("Authorization")); // 프론트에서 읽을 수 있는 헤더

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
