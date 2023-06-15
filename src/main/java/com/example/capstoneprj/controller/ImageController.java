package com.example.capstoneprj.controller;

import com.example.capstoneprj.domain.model.ImageDetails;
import com.example.capstoneprj.repository.ImageRepo;
import com.example.capstoneprj.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDetails> uploadImage(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("content") String content) {
        return imageService.uploadToLocalFileSystem(file, title, content);
    }

    @GetMapping(value = "/getImage/{imageName:.+}", produces = { "image/jpeg", "image/gif", "image/png" })
    public @ResponseBody byte[] getImage(@PathVariable(name = "imageName") String imageName) throws IOException {
        return imageService.getImageWithMediaType(imageName);
    }
    @GetMapping
    public List<ImageDetails> getAllImageDetails() {
        List<ImageDetails> imageDetails = imageRepo.findAll();
        Collections.reverse(imageDetails);
        return imageDetails;
    }
}
