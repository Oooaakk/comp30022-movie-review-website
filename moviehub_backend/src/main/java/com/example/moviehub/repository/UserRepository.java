package com.example.moviehub.repository;

import com.example.moviehub.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findUserByEmail(String email);
}
