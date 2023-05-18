package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends MongoRepository<Quiz, String> {
}
