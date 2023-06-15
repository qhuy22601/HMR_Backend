package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.dto.IdDTO;
import com.example.capstoneprj.domain.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends MongoRepository<Attendance, String> {
    List<Attendance> findAttendanceByUserId(String idDTO);

    Attendance findAttendanceByIsCheckin(boolean check);
}
