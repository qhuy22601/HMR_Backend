package com.example.capstoneprj.service;


import com.example.capstoneprj.domain.dto.*;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.AbsenceRepo;
import com.example.capstoneprj.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
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
        UserModel newUser = useropt.orElseThrow(() -> new RuntimeException("User not found"));

        // Check if there is an existing absence for the same start and end dates
        boolean hasExistingAbsence = absenceRepo.existsByStartDateAndEndDate(absence.getStartDate(), absence.getEndDate());

        if (hasExistingAbsence) {
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
            return responseDTO;
        }
//        UserModel newUser = useropt.get();
        //check if current startDate is great than endDate in database =>true
        absence.setUnread(true);
        absence.setStatus("Requested");
        absence.setLastName(newUser.getLastName());
        absence.setImage(newUser.getImage());
        Absence newAbsence = absenceRepo.save(absence);
        responseDTO.setMess("Success");
        responseDTO.setStatus("Succes");
        responseDTO.setPayload(newAbsence);

        return responseDTO;
    }

    public void dayOff(UserDTO id){
        Optional<UserModel> useropt = userRepo.findById(id.getUserId());
        UserModel newUser = useropt.get();
        boolean hasExistingAbsence = absenceRepo.existsByStartDateAndEndDate(LocalDate.now(), LocalDate.now());
        if(hasExistingAbsence){
            return;
        }
        Absence absence = new Absence();
        absence.setUnread(true);
        absence.setStatus("Requested");
        absence.setStartDate(LocalDate.now());
        absence.setEndDate(LocalDate.now());
        absence.setLastName(newUser.getLastName());
        absence.setImage(newUser.getImage());
        absence.setReason("ko lam quiz");
        absenceRepo.save(absence);
    }

    public ResponseDTO markReaded(){
        ResponseDTO responseDTO = new ResponseDTO();
        List<Absence> listAbs = absenceRepo.findByUnreadTrue();
        if(listAbs.size()<1) {
            log.error("Absence not found");
            responseDTO.setStatus("Fail");
            responseDTO.setMess("Fail");
            responseDTO.setPayload(null);
        }else{
            for(Absence ab : listAbs){
                ab.setUnread(false);
                responseDTO.setStatus("Success");
                responseDTO.setMess("Success");
                responseDTO.setPayload(absenceRepo.save(ab));
            }

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

    public int countDaysOffByUserId(String userId, YearMonth yearMonth) {
        List<Absence> leaves = absenceRepo.findByUserId(userId);

        int totalDaysOff = 0;

        for (Absence leave : leaves) {
            LocalDate startDate = leave.getStartDate();
            LocalDate endDate = leave.getEndDate();

            YearMonth leaveYearMonth = YearMonth.from(startDate);

            if (leaveYearMonth.equals(yearMonth)) {
                int daysOffInMonth = calculateDaysOffInMonth(startDate, endDate, yearMonth);
                totalDaysOff += daysOffInMonth;
            }
        }
        return totalDaysOff;
    }

    public int calculateDaysOffInMonth(LocalDate startDate, LocalDate endDate, YearMonth yearMonth) {
        int startDay = Math.max(startDate.getDayOfMonth(), 1);
        int endDay = Math.min(endDate.getDayOfMonth(), yearMonth.lengthOfMonth());
        return endDay - startDay + 1;
    }

    public void del(IdDTO id){
        Optional<Absence> absence = absenceRepo.findById(id.getId());
        if(absence.isEmpty()){
            throw new RuntimeException("ko co ");
        }else{
            absenceRepo.delete(absence.get());
        }
    }

}

