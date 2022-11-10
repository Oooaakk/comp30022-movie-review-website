package com.example.moviehub.service.Impl;

import com.example.moviehub.service.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void setString(String key, String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value,5, TimeUnit.MINUTES);
    }

    @Override
    public String getString(String key) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get(key);
    }

    @Override
    public Boolean existKey(String key) {
        try{
            return stringRedisTemplate.hasKey(key);
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updateString(String key, String value) {
        stringRedisTemplate.delete(key);
        setString(key, value);
    }

    public Boolean verifyCode(String email, String code){
        if (existKey(email)){
            if (getString(email).equals(code)){
                stringRedisTemplate.delete(email);
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

}
