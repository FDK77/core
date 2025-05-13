package com.example.core.service.rabbit.telegram;


import com.example.core.dto.TelegramRawMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramMessageConsumer {
    private final TelegramMessageHandlerService handlerService;
    @Autowired
    public TelegramMessageConsumer(TelegramMessageHandlerService handlerService) {
        this.handlerService = handlerService;
    }
    @RabbitListener(queues = "telegram.to_core")
    public void handleTelegramMessage(TelegramRawMessageDto rawDto) {
        System.out.println("Получено сообщение от Telegram-модуля: " + rawDto);
        handlerService.handleMessageFromTelegram(rawDto);
    }
}

