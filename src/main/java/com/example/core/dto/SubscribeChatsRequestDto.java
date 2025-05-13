package com.example.core.dto;

import java.util.List;

public class SubscribeChatsRequestDto {
    private List<Long> chatIds;

    public SubscribeChatsRequestDto() {
    }

    public SubscribeChatsRequestDto(List<Long> chatIds) {
        this.chatIds = chatIds;
    }

    public List<Long> getChatIds() {
        return chatIds;
    }

    public void setChatIds(List<Long> chatIds) {
        this.chatIds = chatIds;
    }
}
