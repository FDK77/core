package com.example.core.controller;

import com.example.core.dto.ChatDto;
import com.example.core.dto.ChatSettingDto;
import com.example.core.models.Chat;
import com.example.core.service.ChatService;
import com.example.core.service.TelegramIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/chats")
public class ChatController {
    private ChatService chatService;
    private TelegramIntegrationService telegramService;

    @Autowired
    public ChatController(ChatService chatService, TelegramIntegrationService telegramService) {
        this.chatService = chatService;
        this.telegramService = telegramService;
    }

    @GetMapping
    public ResponseEntity<List<ChatDto>> getAllChats() {
        return ResponseEntity.ok(chatService.getAllChatsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        return chatService.getChatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncChats() {
        chatService.syncChatsWithTelegram();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/settings")
    public ResponseEntity<Void> updateChatSettings(@RequestBody ChatSettingDto chatSettingDto) {
        chatService.updateChatSettings(chatSettingDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<List<Long>> subscribeToChat(@PathVariable Long id) {
        List<Long> result = telegramService.subscribeChats(List.of(id));
        return ResponseEntity.ok(result);
    }

}
