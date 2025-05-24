package com.example.core.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    private Long chatId;
    private String title;
    private Type type;
    private String avatar;
    @Column(columnDefinition = "TEXT")
    private String lastMessage;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Filter> filters = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;



    public Chat() {
    }

    public Chat(Long chatId, String title, Type type, String avatar, String lastMessage, Set<Filter> filters, Set<User> users) {
        this.chatId = chatId;
        this.title = title;
        this.type = type;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.filters = filters;
        this.users = users;
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

    public Set<Filter> getFilters() {
        return filters;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}