package com.example.core.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "filter")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chatId")
    private Chat chat;

    private String requestText;

    public Filter() {
    }

    public Filter(Long id, Chat chat, String requestText) {
        this.id = id;
        this.chat = chat;
        this.requestText = requestText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }
}