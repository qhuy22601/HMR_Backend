package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.AttendanceStatus;
import com.example.capstoneprj.domain.dto.IdDTO;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Attendance;
import com.example.capstoneprj.exception.AttendanceAlreadyMark;
import com.example.capstoneprj.exception.AttendanceNotFound;
import com.example.capstoneprj.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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
        attendance.setIsCheckin(true);
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
            attendance.setIsCheckin(false);
            return attendanceRepo.save(attendance);
        }
    }

    public Attendance testCheckOut(String userId){
        List<Attendance> list = attendanceRepo.findAttendanceByUserId(userId);
        Collections.reverse(list);
        Attendance attendance = list.get(0);
        if(attendance.getIsCheckin() == true){
            attendance.setCheckOut(LocalDateTime.now());
            attendance.setIsCheckin(false);
            return attendanceRepo.save(attendance);
        }else{
            return null;
        }
    }

    public List<Attendance> getAttendance(){
        List<Attendance> listAttendance = attendanceRepo.findAll();
        return listAttendance;
    }

    public List<Attendance> getAttendanceByUserId(String idDTO){
        List<Attendance> list = attendanceRepo.findAttendanceByUserId(idDTO);
        return list;
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

    public void delAll(){
        attendanceRepo.deleteAll();
    }

}
