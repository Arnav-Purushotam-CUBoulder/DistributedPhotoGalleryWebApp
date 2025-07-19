package com.example.photoapp;

import io.minio.errors.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;
    private final MinioService minioService;

    public PhotoController(PhotoRepository photoRepository, MinioService minioService) {
        this.photoRepository = photoRepository;
        this.minioService = minioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getPhotoById(@PathVariable Long id) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new RuntimeException("Photo not found"));
        InputStream inputStream = minioService.getPhoto(photo.getObjectName());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/random")
    public ResponseEntity<InputStreamResource> getRandomPhoto() throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        Photo photo = photoRepository.findRandom();
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        InputStream inputStream = minioService.getPhoto(photo.getObjectName());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException, MinioException, InvalidKeyException, NoSuchAlgorithmException {
        String objectName = UUID.randomUUID().toString();
        minioService.uploadPhoto(objectName, file);
        Photo photo = new Photo(objectName);
        photoRepository.save(photo);
        return ResponseEntity.ok("Photo uploaded successfully");
    }
}
