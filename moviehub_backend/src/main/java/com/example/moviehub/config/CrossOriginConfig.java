package com.example.moviehub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080/")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowedMethods(new String[]{"GET","POST","PUT","DELETE"});
    }
}
