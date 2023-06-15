package com.example.capstoneprj.domain.dto;

import com.example.capstoneprj.domain.model.PayGrade;
import com.example.capstoneprj.domain.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NameDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String Password;
    private String gender;
    private String address;
    private String phoneNumber;
    private PayGrade payGrade;
    private Role role;
}
