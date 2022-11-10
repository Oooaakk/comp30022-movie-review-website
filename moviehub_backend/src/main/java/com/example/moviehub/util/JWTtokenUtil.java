package com.example.moviehub.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.moviehub.collection.JWTSubject;
import com.example.moviehub.collection.User;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class JWTtokenUtil {

    private static final String issuer = "MovieHub";
    private static final String secret = "secretKey";
    public static Map<String, String> generateToken(User user){

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", user.getEmail());
        map.put("refresh", false);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String access_token = JWT.create()
                .withSubject(gson.toJson(map))
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withIssuer(issuer)
                .sign(algorithm);

        map.put("refresh", true);
        String refresh_token = JWT.create()
                .withSubject(gson.toJson(map))
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .withIssuer(issuer)
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;
    }

    public static void validateToken(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm).build().verify(token);
        }catch (Exception e){
            throw e;
        }

    }

    public static String parseJson(String token){
        String subject = JWT.decode(token).getSubject();

        System.out.println("token_subject:" +  subject);
        return  JsonUtil.parseJson(subject).getEmail();
    }

    public static Boolean isRefresh(String token){
        String subject = JWT.decode(token).getSubject();

        return  JsonUtil.parseJson(subject).isRefresh();
    }

    public static JWTSubject decode(String token){
        String jwtSubject = JWT.decode(token).getSubject();
        Gson gson = new Gson();
        JWTSubject subject = gson.fromJson(jwtSubject, JWTSubject.class);
        return subject;
    }
}
