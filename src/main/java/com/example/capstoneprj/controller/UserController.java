package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.AuthorizedDTO;
import com.example.capstoneprj.domain.dto.LoginDTO;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.UserRepo;
import com.example.capstoneprj.service.UserService;
import com.example.capstoneprj.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil util;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody UserModel user){
        return new ResponseEntity<>(userService.save(user),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
            String token = util.generateToken(loginDTO.getEmail());
            Optional<UserModel> userOpt = userRepo.findByEmail(loginDTO.getEmail());
            UserModel newUser = new UserModel();
            newUser.setPassword("");
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Success","Success", new AuthorizedDTO(newUser,token)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Fail","Fail",null),HttpStatus.OK);
        }
    }
}
