package com.example.moviehub.service;

import com.example.moviehub.collection.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    public String addPhoto(String Filename, MultipartFile image,String userid) throws IOException;

    public Photo getPhoto(String id);

    public void deletePhoto(String id);

}
