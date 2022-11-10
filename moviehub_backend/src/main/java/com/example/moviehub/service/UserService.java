package com.example.moviehub.service;

import com.example.moviehub.collection.RegisterForm;
import com.example.moviehub.collection.User;

public interface UserService {
    Boolean registerUser(RegisterForm registerForm);
    Boolean loginUser(User user);

}
