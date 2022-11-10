package com.example.moviehub.service.Impl;


import com.example.moviehub.collection.Photo;
import com.example.moviehub.repository.PhotoRepository;
import com.example.moviehub.service.PhotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public String addPhoto(String Filename, MultipartFile image, String userid) throws IOException {
        Photo photo
                = new Photo();
        photo.setTitle(Filename);
        photo.setPhoto(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
        photo.setUserid(userid);
        return photoRepository.save(photo).getId();
    }

    public Photo getPhoto(String userid) {
        return photoRepository.findByUserid(userid).get(0);
    }

    public void deletePhoto(String id){
        photoRepository.deleteByUserId(id);
    }
}

