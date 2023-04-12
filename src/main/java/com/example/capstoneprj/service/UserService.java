package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.dto.SignUpDTO;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.domain.model.Role;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SalaryService salaryService;

    public ResponseDTO save(SignUpDTO user){
         salaryService.autoAddListUserSalary();
         ResponseDTO responseEntity = new ResponseDTO();
         Optional<UserModel> userOptional = userRepo.findByEmail(user.getEmail());
         if(userOptional.isPresent()){
             log.error("User Existed" + userOptional.get());
             responseEntity.setMess("Fail");
             responseEntity.setStatus("Fail");
             responseEntity.setPayload(null);
             return responseEntity;
         }
         else {
             UserModel newUser = new UserModel();
             newUser.setEmail(user.getEmail());
             newUser.setPayGrade(user.getPayGrade());
             newUser.setPassword(encoder.encode(user.getPassword()));
             newUser.setRole(Role.USER);
             newUser.setBalance((double) 0);
             newUser.setCreatedAt(LocalDate.now());
             responseEntity.setStatus("Success");
             responseEntity.setMess("Success");
             responseEntity.setPayload(userRepo.save(newUser));
             return responseEntity;
         }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepo.findByEmail(email);
        User springUser = null;
        if(user.isEmpty()){
            log.error("User not found" + user.get());
            throw new UsernameNotFoundException("User not found "+email);
        }
        else{
            UserModel newUser = user.get();
            Role role = newUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role.name()));
            springUser = new User(email, newUser.getPassword(), ga);
            return springUser;
        }
    }

    public ResponseDTO getAll() {
        ResponseDTO responseEntity = new ResponseDTO();
        responseEntity.setMess("Sucess");
        responseEntity.setStatus("Sucess");
        responseEntity.setPayload(userRepo.findAll());
        return responseEntity;
    }

    public ResponseDTO findUser(String email, String userName){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<List<UserModel>> optUser = userRepo.findByEmailLikeAndUsernameContaining(email, userName);
        if(optUser.isEmpty()){
            log.error("User not found");
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
        }else{
            responseDTO.setMess("Success");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(optUser);
        }
        return responseDTO;
    }

//    public ResponseDTO absent(Absence absence){
//        ResponseDTO responseDTO = new ResponseDTO();
//
//    }
}
