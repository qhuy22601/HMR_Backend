package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.dto.*;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.repository.UserRepo;
import com.example.capstoneprj.service.UserService;
import com.example.capstoneprj.utils.JwtUtil;
import com.mongodb.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.List;
@RestController
@RequestMapping("/api/auth")
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
    public ResponseEntity<ResponseDTO> save(@RequestBody SignUpDTO   user){
        return new ResponseEntity<>(userService.save(user),HttpStatus.OK);
    }

    @GetMapping("/birth")
    public ResponseEntity<List<UserModel>> getUserBornInCurrentMonth(){
        return new ResponseEntity<>(userService.getUserBorn(),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
            String token = util.generateToken(loginDTO.getEmail());
            Optional<UserModel> userOpt = userRepo.findByEmail(loginDTO.getEmail());
            UserModel newUser = userOpt.get();
            newUser.setPassword("");
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Success","Success", new AuthorizedDTO(newUser,token)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Fail","Fail",null),HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Perform any necessary logout actions (e.g., invalidating session, clearing authentication token)

        if (authentication != null) {
            // Invalidate the session
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            // Clear the authentication token
            SecurityContextHolder.clearContext();
        }

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchUser(@RequestParam @Nullable String email, @RequestParam @Nullable String username){
        return new ResponseEntity<>(userService.findUser(email,username),HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<ResponseDTO> change(@RequestBody BasicInfo userModel){
        return new ResponseEntity<>(userService.changeInfo(userModel),HttpStatus.OK);
    }
//    @PostMapping("/changename")
//    public ResponseEntity<ResponseDTO> changeName(@RequestBody NameDTO nameDTO){
//        return new ResponseEntity<>(userService.changeUserName(nameDTO),HttpStatus.OK);
//    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel>findById(@PathVariable("id") UserDTO id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @PostMapping("/changePass")
    public ResponseEntity<UserModel> changePass(@RequestBody PasswordDTO passwordDTO){
        return new ResponseEntity<>(userService.changePassword(passwordDTO),HttpStatus.OK);
    }

    @PostMapping("/changeAll")
    public ResponseEntity<UserModel> changeAll(@RequestBody NameDTO nameDTO){
        return new ResponseEntity<>(userService.changeAll(nameDTO),HttpStatus.OK);
    }
}
