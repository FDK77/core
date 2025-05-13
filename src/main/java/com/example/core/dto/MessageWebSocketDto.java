package com.example.core.dto;

import com.example.core.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public class MessageWebSocketDto {
    private Long id;
    private String text;
    private String summary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private OffsetDateTime timestamp;
    private Long filterId;

    private Long chatId;
    private User sender;

    public MessageWebSocketDto() {
    }

    public MessageWebSocketDto(Long id, String text, String summary, OffsetDateTime timestamp, Long filterId, Long chatId, User sender) {
        this.id = id;
        this.text = text;
        this.summary = summary;
        this.timestamp = timestamp;
        this.filterId = filterId;
        this.chatId = chatId;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
