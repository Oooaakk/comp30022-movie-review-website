package com.example.moviehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@EnableMongoAuditing
@SpringBootApplication()
public class MoviehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviehubApplication.class, args);
	}

}
