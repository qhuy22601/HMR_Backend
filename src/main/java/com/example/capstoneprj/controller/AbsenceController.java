package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.*;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {
    @Autowired
    private AbsenceService absenceService;

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAll(){
        return new ResponseEntity<>(absenceService.getall(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody Absence absence){
        return new ResponseEntity<>(absenceService.save(absence),HttpStatus.OK);
    }

    @PutMapping("/unread")
    public ResponseEntity<ResponseDTO> unread(){
        return new ResponseEntity<>(absenceService.markReaded(),HttpStatus.OK);
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<ResponseDTO> approve(@PathVariable("id") Approval approval){
        return new ResponseEntity<>(absenceService.approval(approval),HttpStatus.OK);
    }

    @GetMapping("/count-unread")
    public ResponseEntity<Integer> countUnread(){
        return new ResponseEntity<>(absenceService.unreadCount(),HttpStatus.OK);
    }
    @GetMapping("/notif")
    public ResponseEntity<ResponseDTO> notif(){
        return new ResponseEntity<>(absenceService.getUnRead(),HttpStatus.OK);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<Integer> countDaysOffByUserIdAndMonth(
            @PathVariable String userId,
            @RequestParam int year,
            @RequestParam int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int totalDaysOff = absenceService.countDaysOffByUserId(userId, yearMonth);
        return ResponseEntity.ok(totalDaysOff);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> getoff(@PathVariable("id")UserDTO userDTO){
        absenceService.dayOff(userDTO);
        return new ResponseEntity<>("okkkk",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> del(@PathVariable("id")IdDTO idDTO){
        absenceService.del(idDTO);
        return new ResponseEntity<>("okkkk",HttpStatus.OK);

    }
}
