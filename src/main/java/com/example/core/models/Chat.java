package com.example.core.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    private Long chatId;

    private String title;

    private boolean summarize;

    private Type type;

    @ManyToMany
    @JoinTable(
            name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToOne(mappedBy = "chat", cascade = CascadeType.ALL)
    private Filter filter;

    public Chat() {
    }

    public Chat(Long chatId, String title, boolean summarize, Type type, Set<User> users, Filter filter) {
        this.chatId = chatId;
        this.title = title;
        this.summarize = summarize;
        this.type = type;
        this.users = users;
        this.filter = filter;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public boolean isSummarize() {
        return summarize;
    }

    public void setSummarize(boolean summarize) {
        this.summarize = summarize;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}