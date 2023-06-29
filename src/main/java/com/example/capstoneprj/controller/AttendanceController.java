package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.IdDTO;
import com.example.capstoneprj.domain.model.Attendance;
import com.example.capstoneprj.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService){
        this.attendanceService = attendanceService;
    }

    @GetMapping("getall")
    public ResponseEntity<List<Attendance>> getAll(){
        return new ResponseEntity<>(attendanceService.getAttendance(), HttpStatus.OK);
    }

    @PostMapping("/checkin/{userId}")
    public ResponseEntity<Attendance> checkIn(@PathVariable("userId") String userId){
        return new ResponseEntity<>(attendanceService.checkIn(userId),HttpStatus.OK);
    }

    @PostMapping("checkout/{attdId}")
    public ResponseEntity<Attendance> checkOut(@PathVariable("attdId") String attendanceId){
        return new ResponseEntity<>(attendanceService.checkOut(attendanceId),HttpStatus.OK);
    }

    @DeleteMapping("/delAll")
    public String delAll(){
        attendanceService.delAll();
        return "ok";
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<List<Attendance>> getAttById(@PathVariable("id") String idDTO) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByUserId(idDTO);
        Collections.reverse(attendanceList); // Reverse the list

        return new ResponseEntity<>(attendanceList, HttpStatus.OK);
    }


    @PostMapping("/testCheckOut/{id}")
    public ResponseEntity<Attendance> testCheckOut(@PathVariable("id") String userId){
        return new ResponseEntity<>(attendanceService.testCheckOut(userId),HttpStatus.OK);
    }

    @GetMapping("/total-hours-worked/{userId}")
    public double getTotalHoursWorked(@PathVariable String userId) {
        return attendanceService.calculateTotalHoursWorked(userId);
    }

}
