package com.example.core.dto;

public class TelegramRawMessageDto {
    private Long chatId;
    private Long senderId;
    private Long messageId;
    private String text;
    private String timestamp;

    public TelegramRawMessageDto() {
    }

    public TelegramRawMessageDto(Long chatId, Long senderId, Long messageId, String text, String timestamp) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.messageId = messageId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
