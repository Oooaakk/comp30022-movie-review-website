package com.example.moviehub.util;

import com.example.moviehub.collection.JWTSubject;
import com.google.gson.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JsonUtil {


    public static String toJsonString(String message){
        JsonObject response = new JsonObject();
        response.addProperty("message", message);
        return response.toString();
    }

    public static String toJsonString(String message, Object body){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("body", body);
        return gson.toJson(map);
    }

    public static JWTSubject parseJson(String jsonString){
        Gson gson = new Gson();
        JWTSubject subject = gson.fromJson(jsonString, JWTSubject.class);
        return subject;
    }
}
