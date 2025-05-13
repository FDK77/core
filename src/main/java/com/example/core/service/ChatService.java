package com.example.core.service;

import com.example.core.dto.ChatDto;
import com.example.core.dto.ChatSettingDto;
import com.example.core.dto.FilterDto;
import com.example.core.dto.TelegramChatDto;
import com.example.core.models.Chat;
import com.example.core.models.Filter;
import com.example.core.models.Message;
import com.example.core.models.Type;
import com.example.core.repo.ChatRepo;
import com.example.core.repo.MessageRepo;
import com.example.core.service.interfaces.IChatService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {
    private final ChatRepo chatRepo;
    private final FilterService filterService;
    private final MessageRepo messageRepo;
    private final TelegramIntegrationService tgService;

    @Autowired
    public ChatService(ChatRepo chatRepo, FilterService filterService, TelegramIntegrationService tgService, MessageRepo messageRepo) {
        this.chatRepo = chatRepo;
        this.filterService = filterService;
        this.tgService = tgService;
        this.messageRepo = messageRepo;

    }


    public void syncChatsWithTelegram() {
        List<TelegramChatDto> tgChats = tgService.getAllChats();

        List<Long> incomingIds = tgChats.stream()
                .map(TelegramChatDto::getChatId)
                .toList();

        List<Chat> existingChats = chatRepo.findAll();

        List<Chat> chatsToDelete = existingChats.stream()
                .filter(chat -> !incomingIds.contains(chat.getChatId()))
                .toList();

        if (!chatsToDelete.isEmpty()) {
            chatRepo.deleteAll(chatsToDelete);
        }

        List<Chat> chatsToSave = new ArrayList<>();
        for (TelegramChatDto dto : tgChats) {
            Long chatId = dto.getChatId();
            String title = dto.getTitle();
            Type type = dto.getType();
            String avatar = dto.getAvatar();

            Chat chat = chatRepo.findById(chatId).orElseGet(() -> {
                Chat newChat = new Chat();
                newChat.setChatId(chatId);
                return newChat;
            });

            chat.setTitle(title);
            chat.setAvatar(avatar);

            if (type != null) {
                try {
                    chat.setType(type);
                } catch (IllegalArgumentException e) {
                    chat.setType(Type.UNKNOWN);
                }
            }

            chatsToSave.add(chat);
        }

        if (!chatsToSave.isEmpty()) {
            chatRepo.saveAll(chatsToSave);
        }
    }


    public List<Long> getChatIdsWithFilters() {
        return chatRepo.findAllChatIdsWithFilters();
    }

    public void updateChatSettings(ChatSettingDto chatSettingDto) {
        chatRepo.findById(chatSettingDto.getChatId()).ifPresent(chat -> {
            Map<Long, Filter> existingFiltersMap = chat.getFilters().stream()
                    .filter(filter -> filter.getId() != null)
                    .collect(Collectors.toMap(Filter::getId, filter -> filter));

            Set<Filter> updatedFilters = new HashSet<>();

            for (FilterDto filterDto : chatSettingDto.getFilters()) {
                if (filterDto.getId() != null && existingFiltersMap.containsKey(filterDto.getId())) {
                    Filter existingFilter = existingFiltersMap.get(filterDto.getId());
                    existingFilter.setValue(filterDto.getValue());
                    existingFilter.setColor(filterDto.getColor());
                    existingFilter.setName(filterDto.getName());
                    existingFilter.setSummary(filterDto.getSummary());
                    updatedFilters.add(existingFilter);
                } else {
                    Filter newFilter = new Filter();
                    newFilter.setValue(filterDto.getValue());
                    newFilter.setColor(filterDto.getColor());
                    newFilter.setName(filterDto.getName());
                    newFilter.setSummary(filterDto.getSummary());
                    newFilter.setChat(chat);
                    updatedFilters.add(newFilter);
                }
            }

            chat.getFilters().clear();
            chat.getFilters().addAll(updatedFilters);
            chatRepo.save(chat);
        });

        List<Long> chatIdsWithFilters = getChatIdsWithFilters();
        tgService.subscribeChats(chatIdsWithFilters);
    }

    public List<Chat> getAllChats() {
        return chatRepo.findAll();
    }

    public List<ChatDto> getAllChatsDto() {
        List<Chat> chats = chatRepo.findAll();
        return chats.stream()
                .map(chat -> {
                    ChatDto dto = new ChatDto();
                    dto.setChatId(chat.getChatId());
                    dto.setTitle(chat.getTitle());
                    dto.setType(chat.getType());
                    dto.setAvatar(chat.getAvatar());
                    dto.setLastMessage(chat.getLastMessage());
                    dto.setUnreadMessages(chatRepo.checkUnreadByChatId(chat.getChatId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public Optional<Chat> getChatById(Long id) {
        return chatRepo.findById(id);
    }


    public Chat saveChat(Chat chat) {
        return chatRepo.save(chat);
    }

    public void deleteChat(Long id) {
        chatRepo.deleteById(id);
    }
}