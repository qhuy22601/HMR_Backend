package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.*;
import com.example.capstoneprj.domain.model.Absence;
import com.example.capstoneprj.domain.model.Role;
import com.example.capstoneprj.domain.model.UserModel;
import com.example.capstoneprj.exception.JwtExpiredException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
             newUser.setFirstName(user.getFirstName());
             newUser.setLastName(user.getLastName());
//             newUser.setDepartment(user.getDepartment());
             newUser.setPassword(encoder.encode(user.getPassword()));
             newUser.setRole(user.getRole());
             newUser.setImage(user.getImage());
             newUser.setGender(user.getGender());
             newUser.setBirthDate(user.getBirthDate());
             newUser.setBalance((double) 0);
             newUser.setCreatedAt(LocalDate.now());
             responseEntity.setStatus("Success");
             responseEntity.setMess("Success");
             responseEntity.setPayload(userRepo.save(newUser));
             return responseEntity;
         }
    }


    public List<UserModel> getUserBorn(){
        Month currentMonth = LocalDate.now().getMonth();
        LocalDate currentDate = LocalDate.now();
        List<UserModel> allUsers = userRepo.findAll();
        List<UserModel> getUserBornCurrentMonth = allUsers
                .stream()
//                .filter(user -> user.getBirthDate().getMonth() == currentMonth)
//                .filter(user -> {
//                    LocalDate birthDate = user.getBirthDate();
//                    return birthDate != null && birthDate.getMonth() == currentMonth;
//                })
                .filter(user -> {
                    LocalDate birthDate = user.getBirthDate();
                    return birthDate != null && birthDate.getMonth() == currentDate.getMonth()
                            && birthDate.getDayOfMonth() >= currentDate.getDayOfMonth();
                })
                .collect(Collectors.toList());
        return getUserBornCurrentMonth;
    }

    public UserModel findById(UserDTO userDTO){
//        return userRepo.findById(userDTO.getUserId());
        Optional<UserModel> userModel = userRepo.findById(userDTO.getUserId());
        if(userModel.isEmpty()){
            return null;
        }else{
            return userModel.get();
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepo.findByEmail(email);
        User springUser = null;
        if(user.isEmpty()){
            log.error("User not found" + user.get());
            return null;
//            throw new UsernameNotFoundException("User not found "+email);
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
        responseEntity.setMess("Success");
        responseEntity.setStatus("Success");
        responseEntity.setPayload(userRepo.findAll());
        return responseEntity;
    }

    public ResponseDTO findUser(String email, String userName){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<List<UserModel>> optUser = userRepo.findByEmailLikeAndLastNameContaining(email, userName);
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

//    public ResponseDTO changeUserName(NameDTO nameDto){
//        ResponseDTO responseDTO = new ResponseDTO();
//        Optional<UserModel> userOpt = userRepo.findById(nameDto.getId());
//        if(userOpt.isEmpty()){
//            log.error("User not found");
//            responseDTO.setMess("Fail");
//            responseDTO.setStatus("Fail");
//            responseDTO.setPayload(null);
//        }else{
//            UserModel newUser = userOpt.get();
//            newUser.setUsername(nameDto.getUsername());
//            responseDTO.setMess("Success");
//            responseDTO.setStatus("Success");
//            responseDTO.setPayload(userRepo.save(newUser));
//        }
//        return responseDTO;
//    }


    ///////check if password =="" user.getpassword..
    public ResponseDTO changeInfo(BasicInfo userModel){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<UserModel> userOpt = userRepo.findById(userModel.getId());
        if(userOpt.isEmpty()){
            log.error("User not found");
            responseDTO.setMess("Fail");
            responseDTO.setStatus("Fail");
            responseDTO.setPayload(null);
        }else{
            UserModel newUser = userOpt.get();
            newUser.setFirstName(userModel.getFirstName());
            newUser.setLastName(userModel.getLastName());
            newUser.setAddress(userModel.getAddress());
            newUser.setImage(userModel.getImage());
//            newUser.setPassword(encoder.encode(userModel.getPassword()));
            newUser.setPhoneNumber(userModel.getPhoneNumber());
	    newUser.setBirthDate(userModel.getBirthDate());
            newUser.setGender(userModel.getGender());
            responseDTO.setMess("Success");
            responseDTO.setStatus("Success");
            responseDTO.setPayload(userRepo.save(newUser));
        }
        return responseDTO;
    }

    public UserModel changePassword(PasswordDTO passwordDTO){
        Optional<UserModel> userModelOpt = userRepo.findById(passwordDTO.getId());
        if(userModelOpt.isEmpty()){
            return null;
        }else{
            UserModel user = userModelOpt.get();
            user.setPassword(encoder.encode(passwordDTO.getPassword()));
            userRepo.save(user);
            return user;
        }
    }


    public ResponseDTO searchUser(String email, String firstName, String lastName){
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<List<UserModel>> optUser = userRepo.findByEmailLikeOrLastNameContainingOrFirstNameContaining(email, firstName, lastName);
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

    public UserModel changeAll(NameDTO nameDTO){
        Optional<UserModel> userModelOpt = userRepo.findById(nameDTO.getId());
        if(userModelOpt.isEmpty()){
            return null;
        }else{
            UserModel user = userModelOpt.get();
            user.setPassword(encoder.encode(nameDTO.getPassword()));
            user.setFirstName(nameDTO.getFirstName());
            user.setLastName(nameDTO.getLastName());
            user.setAddress(nameDTO.getAddress());
            user.setPhoneNumber(nameDTO.getPhoneNumber());
            user.setBirthDate(LocalDate.parse(nameDTO.getBirthDate()));
            user.setGender(nameDTO.getGender());
            user.setPayGrade(nameDTO.getPayGrade());
            user.setRole(nameDTO.getRole());
            userRepo.save(user);
            return user;
        }
    }
}
