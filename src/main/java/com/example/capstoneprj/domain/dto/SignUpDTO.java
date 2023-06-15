package com.example.capstoneprj.domain.dto;

import com.example.capstoneprj.domain.model.Department;
import com.example.capstoneprj.domain.model.PayGrade;
import com.example.capstoneprj.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String Gender;
    private LocalDate birthDate;
    private PayGrade payGrade;
    private String image;
    private Role role;
}
