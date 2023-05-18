package com.example.capstoneprj.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "quiz")
public class Quiz {

    @Id
    private String id;

    private String question;

    private String answer;
}
