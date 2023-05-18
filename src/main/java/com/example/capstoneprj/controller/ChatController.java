package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.model.Chat;
import com.example.capstoneprj.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate){
        this.chatService = chatService;
        this.messagingTemplate = simpMessagingTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<Chat>> getAll(){
        return new ResponseEntity<>(chatService.getAllMess(), HttpStatus.OK);
    }

//    @PostMapping()
//    @SendTo("/topic/messages")
    @MessageMapping("/chat/public")
    public void sendPublicMessage(Chat message) {
    messagingTemplate.convertAndSend("/topic/public", message);
    chatService.save(message);
}

    @MessageMapping("/chat/private")
    public void sendPrivateMessage(Chat message) {
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/private", message);
        messagingTemplate.convertAndSendToUser(message.getUserName(), "/queue/private", message);
        chatService.save(message);
    }
}
