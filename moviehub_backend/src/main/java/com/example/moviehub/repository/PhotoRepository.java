package com.example.moviehub.repository;

import com.example.moviehub.collection.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
    

    List<Photo> findByUserid(String userid);

    void deleteByUserId(String id);
}
