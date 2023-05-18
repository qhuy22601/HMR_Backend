//package com.example.capstoneprj.socket;
//
//
//import com.example.capstoneprj.domain.model.Chat;
//import com.example.capstoneprj.repository.ChatRepo;
//import com.example.capstoneprj.service.ChatService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//
//@Component
//public class WebSocketEventListener implements ApplicationListener<SessionConnectEvent> {
//
//    private final SimpMessageSendingOperations messagingTemplate;
//
//    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @Override
//    public void onApplicationEvent(SessionConnectEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String sessionId = accessor.getSessionId();
////        String username = // Get the username from the session or authentication
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Chat chatMessage = new Chat(null, username, "joined the chat", LocalDateTime.now());
//        messagingTemplate.convertAndSend("/topic/chat", chatMessage);
//    }
//}