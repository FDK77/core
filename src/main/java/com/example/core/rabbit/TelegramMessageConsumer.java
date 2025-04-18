package com.example.core.rabbit;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.example.core.config.RabbitConfig.TELEGRAM_TO_CORE_QUEUE;

@Component
public class TelegramMessageConsumer {

    private  TelegramMessageHandlerService handlerService;
    private  ObjectMapper objectMapper;


    @Autowired
    public TelegramMessageConsumer(TelegramMessageHandlerService handlerService, ObjectMapper objectMapper) {
        this.handlerService = handlerService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = TELEGRAM_TO_CORE_QUEUE)
    public void handleTelegramMessage(String messageJson) {
        try {
            Map<String, Object> message = objectMapper.readValue(messageJson, Map.class);
            handlerService.handleMessageFromTelegram(message);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке сообщения от Telegram: " + e.getMessage());
        }
    }
}

