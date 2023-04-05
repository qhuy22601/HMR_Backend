package com.example.capstoneprj.domain.dto;

import com.example.capstoneprj.domain.model.UserModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorizedDTO {
    public UserModel userModel;
    public String token;
}
