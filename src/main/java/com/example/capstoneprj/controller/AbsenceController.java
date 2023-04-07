package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.ResponseDTO;
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
}
