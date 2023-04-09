package com.example.capstoneprj.domain.model;

import lombok.*;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Salary {
    @Id
    private String id;

    private PayGrade amount;

    private LocalDate date;

    private String userId;

}
