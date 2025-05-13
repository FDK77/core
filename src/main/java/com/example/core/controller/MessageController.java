package com.example.core.controller;

import com.example.core.dto.MessageDto;
import com.example.core.models.Chat;
import com.example.core.models.Message;
import com.example.core.service.ChatService;
import com.example.core.service.MessageService;
import com.example.core.service.TelegramIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/messages")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<?> getMessagesByChatId(@PathVariable Long id) {
        try {
            List<MessageDto> messages = messageService.getAllMessagesDtoByChatId(id);
            return ResponseEntity.ok(messages);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/filter/{fid}")
    public ResponseEntity<?> getMessagesByFilterId(@PathVariable Long fid) {
        try {
            List<MessageDto> messages = messageService.getAllMessagesDtoByFilterId(fid);
            return ResponseEntity.ok(messages);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/filter/{fid}")
    public ResponseEntity<?> deleteMessagesByFilterId(@PathVariable Long fid) {
        try {
            messageService.deleteAllMessagesForFilter(fid);
            return ResponseEntity.ok("All messages for filter with ID " + fid + " were successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
