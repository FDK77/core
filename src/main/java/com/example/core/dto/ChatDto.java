package com.example.core.dto;

import com.example.core.models.Filter;
import com.example.core.models.Type;
import com.example.core.models.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatDto {
    private Long chatId;
    private String title;
    private Type type;

    private String avatar;

    private String lastMessage;

    private Boolean unreadMessages;
    public ChatDto() {
    }

    public ChatDto(Long chatId, String title, Type type, String avatar, String lastMessage, Boolean unreadMessages) {
        this.chatId = chatId;
        this.title = title;
        this.type = type;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.unreadMessages = unreadMessages;
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Boolean getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(Boolean unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
}
