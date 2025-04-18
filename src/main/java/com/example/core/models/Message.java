package com.example.core.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    private Long id;

    private String text;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chatId")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "userId")
    private User sender;

    public Message() {}

    public Message(String text, LocalDateTime timestamp, Chat chat, User sender) {
        this.text = text;
        this.timestamp = timestamp;
        this.chat = chat;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}