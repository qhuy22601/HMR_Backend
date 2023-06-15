package com.example.capstoneprj.domain.model;

import lombok.*;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDetails {
    @Id
    private String id;

    private String imageUrl;

    private String content;

    private String title;
}
