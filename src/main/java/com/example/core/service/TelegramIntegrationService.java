package com.example.core.service;


import com.example.core.dto.SubscribeChatsRequestDto;
import com.example.core.dto.SubscribedChatsResponseDto;
import com.example.core.dto.TelegramChatDto;
import com.example.core.dto.TelegramUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TelegramIntegrationService {
    private final RestTemplate restTemplate;

    private final AvatarService avatarService;

    @Value("${telegram.service.base-url}")
    private String telegramBaseUrl;
    @Autowired
    public TelegramIntegrationService(RestTemplate restTemplate, AvatarService avatarService) {
        this.restTemplate = restTemplate;
        this.avatarService = avatarService;
    }

    public List<TelegramChatDto> getAllChats() {
        String url = telegramBaseUrl + "/chats";
        ResponseEntity<List<TelegramChatDto>> resp = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<TelegramChatDto> chats = resp.getBody();
        if (chats != null) {
            chats.forEach(chat -> {
                if (chat.getAvatar() != null) {
                    avatarService.downloadAndSaveChatAvatar(chat.getChatId());
                }
            });
        }

        return chats;
    }

    public Optional<TelegramUserDto> getUserById(Long userId) {
        String url = telegramBaseUrl + "/user/" + userId;
        try {
            TelegramUserDto dto = restTemplate.getForObject(url, TelegramUserDto.class);
            if (dto != null && dto.getAvatarPath() != null) {
                avatarService.downloadAndSaveUserAvatar(dto.getUserId());
            }
            return Optional.ofNullable(dto);
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }

    public List<Long> subscribeChats(List<Long> chatIds) {
        String url = telegramBaseUrl + "/subscribe_chats";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SubscribeChatsRequestDto requestDto = new SubscribeChatsRequestDto(chatIds);
        HttpEntity<SubscribeChatsRequestDto> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<SubscribedChatsResponseDto> response = restTemplate
                .postForEntity(url, entity, SubscribedChatsResponseDto.class);

        if (response.getBody() != null && response.getBody().getSubscribedChats() != null) {
            return response.getBody().getSubscribedChats();
        } else {
            return List.of();
        }
    }



}