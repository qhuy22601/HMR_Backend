package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Salary;
import com.example.capstoneprj.service.SalaryService;
import com.example.capstoneprj.service.ScheduleConfig;
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

    @Autowired
    private ScheduleConfig scheduleConfig;

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getall(){
        return new ResponseEntity<>(salaryService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody Salary salary){
        return new ResponseEntity<>(salaryService.save(salary),HttpStatus.OK);
    }

    @PostMapping("/autopay")
    public ResponseEntity<String> autoPay(){
        scheduleConfig.enableAutoPaySalary();
        return ResponseEntity.ok("Auto pay salary has been enabled");
    }
    @PostMapping("/stopautopay")
    public ResponseEntity<String> StopAutoPay(){
        scheduleConfig.disableAutoPaySalary();
        return ResponseEntity.ok("Auto pay salary has been disabled");
    }
//    @PostMapping("/test")
//    public ResponseEntity<ResponseDTO> test(){
//        return new ResponseEntity<>(salaryService.autoPay(), HttpStatus.OK);
//    }

}
