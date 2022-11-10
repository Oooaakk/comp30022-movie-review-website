package com.example.moviehub.controller;



import com.example.moviehub.collection.Photo;
import com.example.moviehub.service.Impl.PhotoServiceImpl;
import com.example.moviehub.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoServiceImpl photoServiceImpl;

    @PostMapping
    public String addPhoto(@RequestParam("image") MultipartFile image, @RequestParam String userid) throws IOException {
        String id = photoServiceImpl.addPhoto(image.getOriginalFilename(),image,userid);
        return  id;
    }

    @GetMapping
    public ResponseEntity<Resource> downloadPhoto(@RequestParam String userid) {
        Photo photo = photoServiceImpl.getPhoto(userid);
        Resource resource
                = new ByteArrayResource(photo.getPhoto().getData());

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, photo.getId())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/edit")
    public String updatePhoto(@RequestParam("image") MultipartFile image,@RequestParam String userid) throws IOException {
        photoServiceImpl.deletePhoto(userid);
        String pid = photoServiceImpl.addPhoto(image.getOriginalFilename(),image,userid);
        return pid;
    }
}
