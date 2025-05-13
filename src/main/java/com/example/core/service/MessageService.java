package com.example.core.service;


import com.example.core.dto.FilterToLlmDto;
import com.example.core.dto.MessageDto;
import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.models.Message;
import com.example.core.repo.MessageRepo;
import com.example.core.service.interfaces.IMessageService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {
    private final MessageRepo messageRepo;
    private final FilterService filterService;
    private final ChatService chatService;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageService(MessageRepo messageRepo, FilterService filterService, ChatService chatService, ModelMapper modelMapper) {
        this.messageRepo = messageRepo;
        this.filterService = filterService;
        this.chatService = chatService;
        this.modelMapper = modelMapper;
    }

    public List<MessageDto> getAllMessagesDtoByChatId(Long chatId) {
        return messageRepo.findAllByChatId(chatId).stream()
                .map(message -> new MessageDto(
                        message.getId(),
                        message.getText(),
                        message.getSummary(),
                        message.getTimestamp(),
                        message.getSender()
                ))
                .toList();
    }

    @Transactional
    public List<MessageDto> getAllMessagesDtoByFilterId(Long filterId) {
        List<Message> messages = messageRepo.findAllByFilterId(filterId);
        messageRepo.markAllAsCheckedByFilterId(filterId);
        return messages.stream()
                .map(m -> modelMapper.map(m, MessageDto.class))
                .collect(Collectors.toList());
    }
    public Optional<Message> getLatestMessageByChat(Chat chat) {
        List<Message> messages = messageRepo.findTop1ByChatOrderByTimestampDesc(chat);
        return messages.isEmpty() ? Optional.empty() : Optional.of(messages.get(0));
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepo.findById(id);
    }

    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    public void deleteMessage(Long id) {
        messageRepo.deleteById(id);
    }
    @Transactional
    public void deleteAllMessagesForFilter(Long filterId) {
        Filter filter = filterService.findById(filterId);
        Chat chat = filter.getChat();
        messageRepo.deleteAllByFilterId(filterId);
        String newLast = getLatestMessageByChat(chat)
                .map(Message::getText)
                .orElse(null);
        chat.setLastMessage(newLast);
        chatService.saveChat(chat);
    }



}
