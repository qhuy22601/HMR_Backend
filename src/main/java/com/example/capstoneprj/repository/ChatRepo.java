package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends MongoRepository<Chat, String> {
    List<Chat> findAllByTimeStamp();
}
