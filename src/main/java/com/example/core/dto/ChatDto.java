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
    private List<FilterDto> filters;

    public ChatDto() {
    }

    public ChatDto(Long chatId, String title, Type type, List<FilterDto> filters) {
        this.chatId = chatId;
        this.title = title;
        this.type = type;
        this.filters = filters;
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

    public List<FilterDto> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDto> filters) {
        this.filters = filters;
    }
}
