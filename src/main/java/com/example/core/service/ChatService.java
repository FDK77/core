package com.example.core.service;

import com.example.core.models.Chat;
import com.example.core.models.Type;
import com.example.core.repo.ChatRepo;
import com.example.core.service.interfaces.IChatService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatService implements IChatService {
    private ChatRepo chatRepo;
    private TelegramIntegrationService tgService;

    @Autowired
    public ChatService(ChatRepo chatRepo, TelegramIntegrationService tgService) {
        this.chatRepo = chatRepo;
        this.tgService = tgService;
    }

    @Autowired


    public void syncChatsWithTelegram() {
        List<Map<String, Object>> telegramChats = tgService.getAllChats();

        List<Long> incomingIds = telegramChats.stream()
                .map(chat -> ((Number) chat.get("chatId")).longValue())
                .toList();

        List<Chat> existingChats = chatRepo.findAll();

        List<Chat> chatsToDelete = existingChats.stream()
                .filter(chat -> !incomingIds.contains(chat.getChatId()))
                .toList();

        if (!chatsToDelete.isEmpty()) {
            chatRepo.deleteAll(chatsToDelete);
        }

        List<Chat> chatsToSave = new ArrayList<>();
        for (Map<String, Object> chatData : telegramChats) {
            Long chatId = ((Number) chatData.get("chatId")).longValue();
            String title = (String) chatData.get("title");
            String typeStr = (String) chatData.get("type");

            Chat chat = chatRepo.findById(chatId).orElseGet(() -> {
                Chat newChat = new Chat();
                newChat.setChatId(chatId);
                newChat.setSummarize(false);
                return newChat;
            });

            chat.setTitle(title);

            if (typeStr != null) {
                try {
                    chat.setType(Type.valueOf(typeStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    chat.setType(Type.UNKNOWN);
                }
            }

            chatsToSave.add(chat);
        }

        if (!chatsToSave.isEmpty()) {
            chatRepo.saveAll(chatsToSave);
        }

    }

    public List<Chat> getAllChats() {
        return chatRepo.findAll();
    }

    public Optional<Chat> getChatById(Long id) {
        return chatRepo.findById(id);
    }

    public List<Long> getChatIdsWithSummarizeOrFilter() {
        return chatRepo.findAllChatIdsWithSummarizeOrFilter();
    }


    public Chat saveChat(Chat chat) {
        return chatRepo.save(chat);
    }

    public void deleteChat(Long id) {
        chatRepo.deleteById(id);
    }
}