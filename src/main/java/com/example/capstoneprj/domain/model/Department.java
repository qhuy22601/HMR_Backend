package com.example.capstoneprj.domain.model;

import com.mongodb.lang.Nullable;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "Department")
public class Department {
    @Id
    private String id;
    private String departName;

    @Nullable
    private UserModel departHead;

    private LocalDate createAt;

    private LocalDate updateAt;

    List<UserModel> listUser = new ArrayList<>();
}
