package com.example.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramIntegrationService {
    private RestTemplate restTemplate;

    @Value("${telegram.service.base-url}")
    private String telegramBaseUrl;

    @Autowired
    public TelegramIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Map<String, Object>> getAllChats() {
        String url = telegramBaseUrl + "/chats";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }

    public Map<String, Object> getUserById(Long userId) {
        String url = telegramBaseUrl + "/user/" + userId;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }


    public List<Long> subscribeChats(List<Long> chatIds) {
        String url = telegramBaseUrl + "/subscribe_chats";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("chatIds", chatIds);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        List<?> rawList = (List<?>) response.getBody().get("subscribedChats");
        return rawList.stream()
                .map(id -> ((Number) id).longValue())
                .toList();
    }


}