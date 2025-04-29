package com.example.core.service;

import com.example.core.dto.LlmResultMessageDto;
import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.models.Message;
import com.example.core.models.User;
import com.example.core.service.interfaces.ILlmResultProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class LlmResultProcessingService implements ILlmResultProcessingService {
    private ChatService chatService;
    private UserService userService;
    private MessageService messageService;

    private FilterService filterService;

    private TelegramIntegrationService telegramIntegrationService;
    @Autowired
    public LlmResultProcessingService(ChatService chatService, UserService userService, MessageService messageService, FilterService filterService, TelegramIntegrationService telegramIntegrationService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
        this.filterService = filterService;
        this.telegramIntegrationService = telegramIntegrationService;
    }

    @Override
    public void processLlmResults(List<LlmResultMessageDto> messages) {
        for (LlmResultMessageDto dto : messages) {
            try {
                Chat chat = chatService.getChatById(dto.getChatid()).orElseThrow(() ->
                        new IllegalStateException("Чат с id " + dto.getChatid() + " не найден"));

                User user = userService.getUserById(dto.getUserid()).orElseGet(() -> {
                    Map<String, Object> telegramUser = telegramIntegrationService.getUserById(dto.getUserid());
                    if (telegramUser == null) {
                        throw new IllegalStateException("Пользователь " + dto.getUserid() + " не найден в Telegram");
                    }

                    String username = toNullIfBlank((String) telegramUser.get("username"));
                    String displayName = toNullIfBlank((String) telegramUser.get("displayName"));
                    String avatarPath = toNullIfBlank((String) telegramUser.get("avatarPath"));

                    User newUser = new User(dto.getUserid(), username, displayName, avatarPath);
                    return userService.saveUser(newUser);
                });
                Filter filter = filterService.findById(dto.getMatch());
                LocalDateTime localTime = dto.getTimestamp().toLocalDateTime();
                Message message = new Message();
                message.setId(dto.getMessageid());
                message.setText(dto.getText());
                message.setTimestamp(dto.getTimestamp());
                message.setChat(chat);
                message.setSender(user);
                message.setSummary(dto.getSummary());
                message.setFilter(filter);

                messageService.saveMessage(message);

            } catch (Exception e) {
                System.err.println("Ошибка обработки сообщения от LLM (messageId=" + dto.getMessageid() + "): " + e.getMessage());
            }
        }
    }

    private String toNullIfBlank(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }

}
