package com.example.moviehub.service.Impl;

import com.example.moviehub.collection.Post;
import com.example.moviehub.repository.PostRepository;
import com.example.moviehub.service.PostService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Post savePost(Post post) {

        return postRepository.save(post);
    }

    public Post updatePost(Post post) {

        return postRepository.save(post);
    }

    public List<Post> getAllPost() {

        return postRepository.findAll();
    }

    public List<Post> getPostByUserId(String userId) {

        return postRepository.findByUserId(userId);
    }

    public List<Post> getPostByName(String movieName) {

        return postRepository.findByMovieName(movieName);
    }

    public List<Document> getAvgRating(String movieName) {
        MatchOperation filterMovieName = Aggregation.match(Criteria.where("_id").is(movieName));

        GroupOperation groupByMovieNameAndSumRating = Aggregation.group("movieName","_id").sum("rating").as("totalRating");

        GroupOperation avgRating = Aggregation.group("_id.movieName").avg("totalRating").as("avgRating");

        Aggregation aggregation = Aggregation.newAggregation(groupByMovieNameAndSumRating,avgRating,filterMovieName);

        List<Document> results = mongoTemplate.aggregate(aggregation, Post.class, Document.class).getMappedResults();
        return results;
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
