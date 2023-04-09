package com.example.capstoneprj.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "_User")
public class UserModel {
    @Id
    private String id;

    private String email;

    private String username;

    private String password;

    private LocalDate hireDate;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime followupDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;
}
