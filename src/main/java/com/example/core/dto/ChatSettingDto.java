package com.example.core.dto;

public class ChatSettingDto {
    private Long chatId;
    private String filter;
    private Boolean summary;

    public ChatSettingDto() {}

    public ChatSettingDto(Long chatId, String filter, Boolean summary) {
        this.chatId = chatId;
        this.filter = filter;
        this.summary = summary;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getSummary() {
        return summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }
}
