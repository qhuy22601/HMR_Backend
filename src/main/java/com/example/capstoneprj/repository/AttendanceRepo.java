package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends MongoRepository<Attendance, String> {
}
