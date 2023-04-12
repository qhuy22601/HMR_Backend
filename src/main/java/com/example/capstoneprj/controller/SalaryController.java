package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Salary;
import com.example.capstoneprj.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.SerializedLambda;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getall(){
        return new ResponseEntity<>(salaryService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody Salary salary){
        return new ResponseEntity<>(salaryService.save(salary),HttpStatus.OK);
    }

    @PostMapping("/autopay")
    public ResponseEntity<ResponseDTO> autoPay(){
        return new ResponseEntity<>(salaryService.autoPay(),HttpStatus.OK);
    }
}
