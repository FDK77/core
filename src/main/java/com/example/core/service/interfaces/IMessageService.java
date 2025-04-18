package com.example.core.service.interfaces;

import com.example.core.dto.MessageDto;
import com.example.core.models.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    List<Message> getAllMessages();
    List<MessageDto> getAllMessagesDtoByChatId(Long id);
    Optional<Message> getMessageById(Long id);
    Message saveMessage(Message message);
    void deleteMessage(Long id);
}
