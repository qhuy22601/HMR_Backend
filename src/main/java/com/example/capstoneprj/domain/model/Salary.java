package com.example.capstoneprj.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Salary {
    @Id
    private String id;

    private Double amount;

    private LocalDate date;

}
