package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.ResponseDTO;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public ResponseDTO save(UserModel user){
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
             user.setPassword(encoder.encode(user.getPassword()));
             UserModel newUser = userRepo.save(user);
             responseEntity.setStatus("Success");
             responseEntity.setMess("Success");
             responseEntity.setPayload(newUser);
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
}
