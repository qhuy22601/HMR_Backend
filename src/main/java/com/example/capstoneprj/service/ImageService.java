package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.model.ImageDetails;
import com.example.capstoneprj.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    private final String storageDirectoryPath = "./uploads";
    private final ImageRepo imageDetailsRepository;

    @Autowired
    public ImageService(ImageRepo imageDetailsRepository) {
        this.imageDetailsRepository = imageDetailsRepository;
    }

    public ResponseEntity<ImageDetails> uploadToLocalFileSystem(MultipartFile file, String title, String content) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path storageDirectory = Path.of(storageDirectoryPath);

        if (!Files.exists(storageDirectory)) {
            try {
                Files.createDirectories(storageDirectory);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(null);
            }
        }

        Path destination = storageDirectory.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/getImage/")
                .path(fileName)
                .toUriString();

        ImageDetails imageDetails = new ImageDetails();
        imageDetails.setImageUrl(fileDownloadUri);
        imageDetails.setTitle(title);
        imageDetails.setContent(content);

        ImageDetails savedImageDetails = imageDetailsRepository.save(imageDetails);
        return ResponseEntity.ok(savedImageDetails);
    }

    public byte[] getImageWithMediaType(String imageName) throws IOException {
        Path imagePath = Path.of(storageDirectoryPath).resolve(imageName);
        return Files.readAllBytes(imagePath);
    }
}
