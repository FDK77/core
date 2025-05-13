package com.example.core.service;

import com.example.core.dto.LlmResultMessageDto;
import com.example.core.dto.MessageWebSocketDto;
import com.example.core.dto.TelegramUserDto;
import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.models.Message;
import com.example.core.models.User;
import com.example.core.service.interfaces.ILlmResultProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class LlmResultProcessingService implements ILlmResultProcessingService {
    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;

    private final FilterService filterService;

    private final SimpMessagingTemplate messagingTemplate;

    private final TelegramIntegrationService telegramIntegrationService;

    @Autowired
    public LlmResultProcessingService(ChatService chatService, UserService userService, MessageService messageService, FilterService filterService, SimpMessagingTemplate messagingTemplate, TelegramIntegrationService telegramIntegrationService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
        this.filterService = filterService;
        this.messagingTemplate = messagingTemplate;
        this.telegramIntegrationService = telegramIntegrationService;
    }



    @Override
    public void processLlmResults(List<LlmResultMessageDto> messages) {
        for (LlmResultMessageDto dto : messages) {
            try {
                Chat chat = chatService.getChatById(dto.getChatid())
                        .orElseThrow(() ->
                                new IllegalStateException("Чат с id " + dto.getChatid() + " не найден"));

                User user = userService.getUserById(dto.getUserid()).orElseGet(() -> {
                    TelegramUserDto tgUser = telegramIntegrationService
                            .getUserById(dto.getUserid())
                            .orElseThrow(() ->
                                    new IllegalStateException("Пользователь " + dto.getUserid() + " не найден в Telegram"));

                    String phoneNumber = toNullIfBlank(tgUser.getPhoneNumber());
                    String username = toNullIfBlank(tgUser.getUsername());
                    String displayName = toNullIfBlank(tgUser.getDisplayName());
                    String avatarPath = toNullIfBlank(tgUser.getAvatarPath());

                    User newUser = new User(dto.getUserid(), username, displayName, avatarPath, phoneNumber);
                    return userService.saveUser(newUser);
                });
                Filter filter = filterService.findById(dto.getMatch());
                Message message = new Message();
                message.setId(dto.getMessageid());
                message.setText(dto.getText());
                message.setTimestamp(dto.getTimestamp());
                message.setChat(chat);
                message.setSender(user);
                message.setSummary(dto.getSummary());
                message.setFilter(filter);
                message.setChecked(false);
                chat.setLastMessage(message.getText());
                messageService.saveMessage(message);
                chatService.saveChat(chat);
                MessageWebSocketDto wsDto = new MessageWebSocketDto();
                wsDto.setId(message.getId());
                wsDto.setText(message.getText());
                wsDto.setSummary(message.getSummary());
                wsDto.setTimestamp(message.getTimestamp());
                wsDto.setFilterId(filter.getId());
                wsDto.setChatId(chat.getChatId());
                wsDto.setSender(user);
                messagingTemplate.convertAndSend("/topic/messages", wsDto);
            } catch (Exception e) {
                System.err.println("Ошибка обработки сообщения от LLM (messageId=" + dto.getMessageid() + "): " + e.getMessage());
            }
        }
    }


    private String toNullIfBlank(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }

}
