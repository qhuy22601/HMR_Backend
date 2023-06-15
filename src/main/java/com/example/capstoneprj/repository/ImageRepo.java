package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.ImageDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends MongoRepository<ImageDetails, String> {
}
