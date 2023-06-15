package com.example.capstoneprj.domain.model;

import com.example.capstoneprj.domain.dto.AttendanceStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "attendance")
public class Attendance {
    @Id
    private String id;
    private String userId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Boolean isCheckin;
}
