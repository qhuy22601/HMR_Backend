package com.example.capstoneprj.domain.dto;


import com.example.capstoneprj.domain.model.PayGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfo {

    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String gender;

    private String image;


}
