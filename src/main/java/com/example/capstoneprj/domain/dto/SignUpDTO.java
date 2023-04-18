package com.example.capstoneprj.domain.dto;

import com.example.capstoneprj.domain.model.PayGrade;
import com.example.capstoneprj.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String email;
    private String password;
    private String username;
    private PayGrade payGrade;
    private Role role;
}
