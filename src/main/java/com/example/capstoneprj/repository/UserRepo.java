package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}
