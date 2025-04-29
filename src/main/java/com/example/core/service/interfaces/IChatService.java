package com.example.core.service.interfaces;

import com.example.core.models.Chat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IChatService {

    List<Chat> getAllChats();

    Optional<Chat> getChatById(Long id);

    void syncChatsWithTelegram();

    Chat saveChat(Chat chat);

    void deleteChat(Long id);
}
