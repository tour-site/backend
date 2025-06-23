// 📁 src/main/java/com/project/tour/util/KakaoUtil.java
package com.project.tour.util;

import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper; // 자동매핑용으로 만든 코드
import com.project.tour.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoUtil {

    private final WebClient.Builder webClientBuilder;
    // private final ObjectMapper objectMapper; // 자동매핑용으로 만든코드

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private static final String KAKAO_USERINFO_URI = "https://kapi.kakao.com/v2/user/me";

    // 1️⃣ 인가코드로 access_token 요청
    public String getAccessToken(String code) {
        JsonNode response = webClientBuilder.build()
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=authorization_code"
                        + "&client_id=" + clientId
                        + "&redirect_uri=" + redirectUri
                        + "&code=" + code)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return response.get("access_token").asText();
    }

    // 2️⃣ access_token으로 사용자 정보 요청
    public KakaoUserInfoDto getUserInfo(String accessToken) {
        JsonNode userInfo = webClientBuilder.build()
                .get()
                .uri(KAKAO_USERINFO_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode kakaoAccount = userInfo.get("kakao_account");
        JsonNode profile = kakaoAccount.get("profile");

        return KakaoUserInfoDto.builder()
                .email(getSafeText(kakaoAccount, "email"))
                .name(getSafeText(kakaoAccount, "name"))
                .nickname(profile != null ? getSafeText(profile, "nickname") : null)
                .profileImage(profile != null ? getSafeText(profile, "profile_image_url") : null)
                .gender(getSafeText(kakaoAccount, "gender"))
                .birthday(getSafeText(kakaoAccount, "birthday"))
                .phoneNumber(getSafeText(kakaoAccount, "phone_number"))
                .build();
    }

    private String getSafeText(JsonNode node, String fieldName) {
        return node.has(fieldName) && !node.get(fieldName).isNull()
                ? node.get(fieldName).asText()
                : null;
    }
}
