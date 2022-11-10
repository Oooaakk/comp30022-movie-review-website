package com.example.moviehub.service;

public interface RedisService {

    public void setString(String key, String value);

    public String getString(String key);

    public Boolean existKey(String key);

    public void updateString(String key, String value);
}
