package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.Approval;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.dto.Unread;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDTO> unread(@RequestBody Unread unread){
        return new ResponseEntity<>(absenceService.markReaded(unread),HttpStatus.OK);
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
}
