package com.example.moviehub.collection;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "post")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

public class Post {

    @Id
    private String id;

    private String userId;
    private String movieName;

    private String content;


    private double rating;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    public Post(String id, String userId, String movieName, String content, double rating, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.userId = userId;
        this.movieName = movieName;
        this.content = content;
        this.rating = rating;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
