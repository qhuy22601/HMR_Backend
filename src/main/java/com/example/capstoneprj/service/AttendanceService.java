package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.AttendanceStatus;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Attendance;
import com.example.capstoneprj.exception.AttendanceAlreadyMark;
import com.example.capstoneprj.exception.AttendanceNotFound;
import com.example.capstoneprj.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    public Attendance checkIn(String userId){
        Attendance attendance = new Attendance();
        attendance.setUserId(userId);
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.PRESENT);
        return attendanceRepo.save(attendance);
    }

    public Attendance checkOut(String attendanceId){
        Optional<Attendance> attendanceOpt = attendanceRepo.findById(attendanceId);
        if(attendanceOpt.isEmpty()){
            throw new AttendanceNotFound("Attendance not found");
        }else{
            Attendance attendance = attendanceOpt.get();
            if(attendance.getCheckOut()!=null){
                throw new AttendanceAlreadyMark("Attendance already marked with ID: " + attendanceId);
            }
            attendance.setCheckOut(LocalDateTime.now());
            return attendanceRepo.save(attendance);
        }
    }

    public List<Attendance> getAttendance(){
        List<Attendance> listAttendance = attendanceRepo.findAll();
        return listAttendance;
    }

    public ResponseDTO delAttendance(){
        ResponseDTO responseDTO = new ResponseDTO();
        return responseDTO;
    }

    public ResponseDTO updateAttendance(){
        ResponseDTO responseDTO = new ResponseDTO();
        return responseDTO;
    }

    public ResponseDTO getDailyAttendance(){
        ResponseDTO responseDTO = new ResponseDTO();
        return responseDTO;
    }

    public ResponseDTO getMonthlyAttendance(){
        ResponseDTO responseDTO = new ResponseDTO();
        return responseDTO;
    }


}
