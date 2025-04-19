package com.example.core.rabbit.telegram;

import com.example.core.dto.LlmRequestDto;
import com.example.core.dto.TelegramRawMessageDto;
import com.example.core.models.Chat;
import com.example.core.models.Message;
import com.example.core.models.User;
import com.example.core.rabbit.llm.LlmProducerService;
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

    private ChatService chatService;
    private LlmProducerService llmProducerService;

    @Autowired
    public TelegramMessageHandlerService(ChatService chatService, LlmProducerService llmProducerService) {
        this.chatService = chatService;
        this.llmProducerService = llmProducerService;
    }

    public void handleMessageFromTelegram(TelegramRawMessageDto rawdto) {
        try {
            Long chatId = rawdto.getChatId();
            Long senderId = rawdto.getSenderId();
            Long messageId = rawdto.getMessageId();
            String text = rawdto.getText();
            String timestamp = rawdto.getTimestamp();

            Chat chat = chatService.getChatById(chatId).orElseThrow(() ->
                    new IllegalStateException("Chat with id " + chatId + " not found"));

            String filter = chat.getFilter();
            boolean summary = chat.isSummarize();

            LlmRequestDto dto = new LlmRequestDto(
                    messageId,
                    chatId,
                    senderId,
                    text,
                    filter,
                    summary,
                    timestamp
            );

            System.out.println("Отправка сообщения в очередь core.to_llm: " + dto);

            llmProducerService.sendToLlm(dto);

        } catch (Exception e) {
            System.err.println("Ошибка обработки сообщения от Telegram: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String toNullIfBlank(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}