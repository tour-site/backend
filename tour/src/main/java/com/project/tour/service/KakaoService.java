package com.project.tour.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.tour.dto.PlaceDTO;

@Service
public class KakaoService {

    public Map<String, Object> getPlacesByCategory(String category, int page) {
        List<PlaceDTO> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        String kakaoCategoryCode = "FD6"; // 음식점 코드
        if ("stay".equals(category))
            kakaoCategoryCode = "AD5"; // 숙소

        String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json";
        String apiKey = "KakaoAK 5f4736719b17b503c8996d32b4c7534d";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("category_group_code", kakaoCategoryCode)
                .queryParam("x", "129.0756")
                .queryParam("y", "35.1796")
                .queryParam("radius", 5000)
                .queryParam("size", 10)
                .queryParam("page", page);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                JsonNode.class);

        JsonNode body = response.getBody();
        if (body == null) {
            throw new RuntimeException("Kakao API response body is null");
        }

        JsonNode documents = body.get("documents");
        JsonNode meta = body.get("meta");
        boolean isEnd = meta.get("is_end").asBoolean();

        for (JsonNode doc : documents) {
            String name = doc.get("place_name").asText();
            double lat = doc.get("y").asDouble();
            double lng = doc.get("x").asDouble();
            list.add(new PlaceDTO(name, lat, lng));
        }

        result.put("places", list);
        result.put("isEnd", isEnd);
        result.put("page", page);

        return result;
    }
}
