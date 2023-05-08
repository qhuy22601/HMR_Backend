package com.example.capstoneprj.service;


import com.example.capstoneprj.domain.dto.Approval;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.dto.Unread;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.AbsenceRepo;
import com.example.capstoneprj.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AbsenceService {
    @Autowired
    private AbsenceRepo absenceRepo;

    @Autowired
    private UserRepo userRepo;

    public ResponseDTO getall(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMess("Success");
        responseDTO.setStatus("Success");
        responseDTO.setPayload(absenceRepo.findAll());
//        log.info(String.valueOf(unreadCount()));
        return responseDTO;
    }

    public ResponseDTO getById(String id){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Absence> absenceOpt= absenceRepo.findById(id);
        if(absenceOpt.isEmpty()){
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
        }else{
            Absence newAbs = absenceOpt.get();
            responseDTO.setMess("Success");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(newAbs);
        }
        return responseDTO;
    }

    public ResponseDTO save(Absence absence){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<UserModel> useropt = userRepo.findById(absence.getUserId());
//        Optional<Absence> absenceOpt = absenceRepo.findByAbsenceDateBetween(absence.getStartDate(), absence.getEndDate());

        UserModel newUser = useropt.get();
        //check if current startDate is great than endDate in database =>true
        absence.setUnread(true);
        absence.setStatus("Requested");
        absence.setUserName(newUser.getUsername());
        absence.setImage(newUser.getImage());
        Absence newAbsence = absenceRepo.save(absence);
        responseDTO.setMess("Success");
        responseDTO.setStatus("Succes");
        responseDTO.setPayload(newAbsence);

        return responseDTO;
    }

    public ResponseDTO markReaded(Unread absence){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Absence> absenceOpt = absenceRepo.findById(absence.getAbsenceId());
        if(absenceOpt.isEmpty()) {
            log.error("Absence not found");
            responseDTO.setStatus("Fail");
            responseDTO.setMess("Fail");
            responseDTO.setPayload(null);
        }else{
            Absence ab = absenceOpt.get();
            ab.setUnread(false);
            responseDTO.setStatus("Success");
            responseDTO.setMess("Success");
            responseDTO.setPayload(absenceRepo.save(ab));
        }
        return responseDTO;
    }

    public ResponseDTO approval(Approval approval){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Absence> absenceOpt = absenceRepo.findById(approval.getAbsenceId());
        if(absenceOpt.isEmpty()) {
            log.error("Absence not found");
            responseDTO.setStatus("Fail");
            responseDTO.setMess("Fail");
            responseDTO.setPayload(null);
        }else{
            Absence ab = absenceOpt.get();
            ab.setStatus("Approved");
            responseDTO.setStatus("Success");
            responseDTO.setMess("Success");
            responseDTO.setPayload(absenceRepo.save(ab));
        }
        return responseDTO;
    }

    public int unreadCount(){
       List<Absence> absenceOpt = absenceRepo.findByUnreadTrue();

       int cnt = absenceOpt.size();
       return cnt;
    }

    public ResponseDTO getUnRead(){
        ResponseDTO responseDTO = new ResponseDTO();
        List<Absence> unreadAbs = absenceRepo.findByUnreadTrue();
        if(unreadAbs.size()>0){
            responseDTO.setMess("Success");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(unreadAbs);
        }else{
            log.error("Absence not found");
            responseDTO.setStatus("Fail");
            responseDTO.setMess("Fail");
            responseDTO.setPayload(null);
        }
        return responseDTO;
    }
}

