package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.dto.UserDTO;
import com.example.capstoneprj.domain.model.Salary;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.SalaryRepo;
import com.example.capstoneprj.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class SalaryService {
    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private UserRepo userRepo;


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
            log.info("value of salary: " + salary.getAmount().getValue());
        }
        return responseDTO;
    }


    public List<Salary> autoAddListUserSalary(){
        List<UserModel> userList  = userRepo.findAll();
        List<Salary> salaryList = salaryRepo.findAll();
        List<String> idUsers = new ArrayList<>();
        if (userList.size()>0){
            for(UserModel user : userList){
                for(Salary salary : salaryList){
                    if(user.getPayGrade() == salary.getAmount()){
                        idUsers.add(user.getId());
                        salary.setListUser(idUsers);
                        salaryRepo.save(salary);
                    }
                }
            }
            return salaryList;
        }
       else {
           return salaryList;
        }

    }
    public ResponseDTO getAll(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("Success");
        responseDTO.setMess("Success");
        responseDTO.setPayload(salaryRepo.findAll());
        return responseDTO;
    }

//    @Scheduled(cron = "0 0 1 * * ?")//every 1st day of month
//    @Scheduled(cron = "0 * * * * ?") // runs every minute at the beginning of the minute
//    public ResponseDTO autoPay(){
//        ResponseDTO responseDTO = new ResponseDTO();
//        List<Salary> salaryList = salaryRepo.findAll();
//        for(Salary salary: salaryList){
//                for (String userId : salary.getListUser()) {
//                    Optional<UserModel> newUser = userRepo.findById(userId);
//                    UserModel userModel = newUser.get();
//                    userModel.setBalance(userModel.getBalance() + userModel.getPayGrade().getValue());
//                    responseDTO.setStatus("Success");
//                    responseDTO.setMess("Sucess");
//                    responseDTO.setPayload(userRepo.save(userModel));
//                }
//        }
//        return responseDTO;
//    }

}
