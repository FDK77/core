package com.example.core.rabbit;

import com.example.core.models.Chat;
import com.example.core.models.Message;
import com.example.core.models.User;
import com.example.core.repo.ChatRepo;
import com.example.core.repo.MessageRepo;
import com.example.core.repo.UserRepo;
import com.example.core.service.ChatService;
import com.example.core.service.MessageService;
import com.example.core.service.TelegramIntegrationService;
import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramMessageHandlerService {

    private MessageService messageService;
    private ChatService chatService;
    private UserService userService;
    private TelegramIntegrationService telegramIntegrationService;

    @Autowired
    public TelegramMessageHandlerService(MessageService messageService, ChatService chatService, UserService userService, TelegramIntegrationService telegramIntegrationService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userService = userService;
        this.telegramIntegrationService = telegramIntegrationService;
    }

    public void handleMessageFromTelegram(Map<String, Object> messageData) {
        try {
            Long chatId = ((Number) messageData.get("chatId")).longValue();
            Long senderId = ((Number) messageData.get("senderId")).longValue();
            Long messageId = ((Number) messageData.get("messageId")).longValue();
            String text = (String) messageData.get("text");
            String timestamp = (String) messageData.get("timestamp");

            // Проверка: существует ли чат
            Chat chat = chatService.getChatById(chatId).orElseThrow(() ->
                    new IllegalStateException("Chat with id " + chatId + " not found"));

            // Получить пользователя из БД, либо запросить у Telegram и сохранить
            User user = userService.getUserById(senderId).orElseGet(() -> {
                Map<String, Object> telegramUser = telegramIntegrationService.getUserById(senderId);
                if (telegramUser == null) {
                    throw new IllegalStateException("User " + senderId + " not found in Telegram");
                }

                String username = toNullIfBlank((String) telegramUser.get("username"));
                String displayName = toNullIfBlank((String) telegramUser.get("displayName"));
                String avatarPath = toNullIfBlank((String) telegramUser.get("avatarPath"));

                User newUser = new User(senderId, username, displayName, avatarPath);
                return userService.saveUser(newUser);
            });


            Message message = new Message();
            message.setId(messageId);
            message.setText(text);
            message.setTimestamp(OffsetDateTime.parse(timestamp).toLocalDateTime());
            message.setChat(chat);
            message.setSender(user);

            messageService.saveMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to handle Telegram message: " + e.getMessage());
        }
    }
    private String toNullIfBlank(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}