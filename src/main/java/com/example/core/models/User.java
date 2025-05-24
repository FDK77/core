package com.example.core.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tg_user")
public class User {
    @Id
    private Long userId;
    private String username;
    private String displayName;
    private String avatarPath;
    private String phoneNumber;

    public User() {
    }

    public User(Long userId, String username, String displayName, String avatarPath, String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarPath = avatarPath;
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}