package com.example.capstoneprj.service;


import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.repository.AbsenceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AbsenceService {
    @Autowired
    private AbsenceRepo absenceRepo;

    public ResponseDTO getall(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMess("Success");
        responseDTO.setStatus("Success");
        responseDTO.setPayload(absenceRepo.findAll());
        return responseDTO;
    }

    public ResponseDTO save(Absence absence){
        ResponseDTO responseDTO = new ResponseDTO();
//        Optional<Absence> absenceOpt = absenceRepo.findByAbsenceDateBetween(absence.getStartDate(), absence.getEndDate());

        //check if current startDate is great than endDate in database =>true
        Absence newAbsence = absenceRepo.save(absence);
        responseDTO.setMess("Success");
        responseDTO.setStatus("Succes");
        responseDTO.setPayload(newAbsence);

        return responseDTO;
    }
}
