package com.example.core.rabbit.telegram;


import com.example.core.dto.TelegramRawMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.example.core.config.RabbitConfig.TELEGRAM_TO_CORE_QUEUE;

@Component
public class TelegramMessageConsumer {

    private TelegramMessageHandlerService handlerService;
    private  ObjectMapper objectMapper;


    @Autowired
    public TelegramMessageConsumer(TelegramMessageHandlerService handlerService, ObjectMapper objectMapper) {
        this.handlerService = handlerService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "telegram.to_core")
    public void handleTelegramMessage(TelegramRawMessageDto rawDto) {
        System.out.println("Получено сообщение от Telegram-модуля: " + rawDto);
        handlerService.handleMessageFromTelegram(rawDto);
    }

}

