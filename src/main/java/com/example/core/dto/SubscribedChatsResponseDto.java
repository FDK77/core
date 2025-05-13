package com.example.core.dto;

import java.util.List;

public class SubscribedChatsResponseDto {
    private List<Long> subscribedChats;

    public SubscribedChatsResponseDto() {
    }

    public List<Long> getSubscribedChats() {
        return subscribedChats;
    }

    public void setSubscribedChats(List<Long> subscribedChats) {
        this.subscribedChats = subscribedChats;
    }
}
