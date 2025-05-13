package com.example.core.dto;

import com.example.core.models.Type;

public class TelegramChatDto {
    private Long chatId;
    private String title;
    private Type type;
    private String avatar;

    public TelegramChatDto(Long chatId, String title, Type type, String avatar) {
        this.chatId = chatId;
        this.title = title;
        this.type = type;
        this.avatar = avatar;
    }

    public TelegramChatDto() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
