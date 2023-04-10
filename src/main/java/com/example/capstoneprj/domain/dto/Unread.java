package com.example.capstoneprj.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Unread {
    private String absenceId;
    private boolean unread;
}
