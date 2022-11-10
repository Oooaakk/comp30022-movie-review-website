package com.example.moviehub.service;

public interface EmailService {
    void sendMail(String to, String subject, String text);
}