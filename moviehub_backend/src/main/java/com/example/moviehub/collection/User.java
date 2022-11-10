package com.example.moviehub.collection;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

@Data
@Builder
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class User {

    public enum Gender{
        MALE, FEMALE, ANONYMOUS;
    }
    @Id
    private String id;
    private String username;

    private String email;
    private String password;

    private Integer age;
    private String photoId;

    private Gender gender;


    public User(){};

    public User(String id, String username, String password, String email, String photoId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.photoId = photoId;
    }

    public User(String id, String username, String email, String password, Integer age, String photoId, Gender gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.photoId = photoId;
        this.gender = gender;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public User(String username, String email, String password, Integer age, Gender gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    public User(String username, String email, String password, Integer age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
