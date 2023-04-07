package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Salary;
import com.example.capstoneprj.repository.SalaryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class SalaryService {
    @Autowired
    private SalaryRepo salaryRepo;

    public ResponseDTO getAll(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Success");
        responseDTO.setMess("Success");
        responseDTO.setPayload(salaryRepo.findAll());
        return responseDTO;
    }

    public ResponseDTO save(Salary salary){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Salary> salaryOpt = salaryRepo.findByAmount(salary.getAmount());
        if(salaryOpt.isPresent()){
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
            log.error("Salary amount Existed");
        }else {
            salary.setDate(LocalDate.now());
            Salary newSalary = salaryRepo.save(salary);
            responseDTO.setStatus("Success");
            responseDTO.setMess("Success");
            responseDTO.setPayload(newSalary);
        }
        return responseDTO;
    }


}
