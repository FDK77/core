package com.example.core.dto;

import java.util.List;

public class ChatSettingDto {
    private Long chatId;
    private List<FilterDto> filters;

    public ChatSettingDto() {}

    public ChatSettingDto(Long chatId, List<FilterDto> filters) {
        this.chatId = chatId;
        this.filters = filters;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<FilterDto> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDto> filters) {
        this.filters = filters;
    }
}
