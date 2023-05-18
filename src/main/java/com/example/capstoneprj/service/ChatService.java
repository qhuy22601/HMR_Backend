package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.model.Chat;
import com.example.capstoneprj.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepo chatRepo;

    public List<Chat> getAllMess(){
        return chatRepo.findAllByTimeStamp();
    }

    public void save(Chat chat){
        chat.setTimeStamp(LocalDateTime.now());
        chatRepo.save(chat);
    }
}
