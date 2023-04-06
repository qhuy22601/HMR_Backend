package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Department;
import com.example.capstoneprj.repository.DepartmentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public ResponseDTO getAll(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Success");
        responseDTO.setMess("Success");
        responseDTO.setPayload(departmentRepo.findAll());
        return responseDTO;
    }

    public ResponseDTO getByName(String name){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Department> departOpt = departmentRepo.findByDepartName(name);
        if(departOpt.isEmpty()){
            log.error("Department not found");
            responseDTO.setStatus("Fail");
            responseDTO.setMess("Fail");
            responseDTO.setPayload(null);
        }else{
            responseDTO.setMess("Success");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(departOpt);
        }
        return responseDTO;
    }

    public ResponseDTO save(Department department){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Department> departmentOpt = departmentRepo.findByDepartName(department.getDepartName());
        if (departmentOpt.isPresent()){
            log.error("Department");
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
        }else{
            Department newDepartment = departmentRepo.save(department);
            responseDTO.setMess("Sucess");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(newDepartment);

        }
        return responseDTO;
    }

}
