// 📁 src/main/java/com/project/tour/util/KakaoUtil.java
package com.project.tour.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoUtil {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final WebClient webClient = WebClient.create();

    // 1️⃣ 인가코드로 access_token 요청
    public String getAccessToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=authorization_code"
                        + "&client_id=" + clientId
                        + "&redirect_uri=" + redirectUri
                        + "&code=" + code)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block()
                .get("access_token")
                .asText();
    }

    // 2️⃣ access_token으로 사용자 정보 요청
    public JsonNode getUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
