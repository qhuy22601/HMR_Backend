package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Department;
import com.example.capstoneprj.service.DepartmentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        return new ResponseEntity<>(departmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<ResponseDTO> getByName(@RequestParam String name){
        return new ResponseEntity<>(departmentService.getByName(name),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save (@RequestBody Department department){
        return new ResponseEntity<>(departmentService.save(department),HttpStatus.OK);
    }
}
