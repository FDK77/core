package com.example.core.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tg_user")
@Data
public class User {
    @Id
    private Long userId;
    private String username;
    private String displayName;
    private String avatarPath;

    public User() {
    }

    public User(Long userId, String username, String displayName, String avatarPath) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.avatarPath = avatarPath;
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

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}