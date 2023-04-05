package com.example.capstoneprj.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseDTO {
    private String mess;
    private String status;
    private Object payload;
}
