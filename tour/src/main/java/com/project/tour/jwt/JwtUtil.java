// 📁 src/main/java/com/project/tour/jwt/JwtUtil.java
package com.project.tour.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyPlain;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
        System.out.println("[JwtUtil] ✅ JWT Key initialized");
    }

    public String createToken(String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now) // ✅ 생성 시간
                .setExpiration(expiry) // ✅ 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("[JwtUtil] ❌ 토큰 유효성 실패: " + e.getMessage());
            return false;
        }
    }

    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String getUserRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            System.out.println("[JwtUtil] ❗ 쿠키 없음");
            return null;
        }

        for (var cookie : request.getCookies()) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        System.out.println("[JwtUtil] ❗ token 쿠키 없음");
        return null;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }
}
