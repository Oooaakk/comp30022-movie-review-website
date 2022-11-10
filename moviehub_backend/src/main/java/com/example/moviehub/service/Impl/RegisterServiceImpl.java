package com.example.moviehub.service.Impl;

import com.example.moviehub.collection.RegisterForm;
import com.example.moviehub.collection.User;
import com.example.moviehub.service.RegisterService;
import com.example.moviehub.util.VerificationCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {


    private final EmailServiceImpl emailService;
    private final UserServiceImpl userService;
    private final RedisServiceImpl redisService;

    @Override
    public void sendEmailVerificationCode(String email) {

        if (userService.getUserByEmail(email) != null){
            throw new RuntimeException("Email already registered");
        }

        String code = VerificationCodeUtil.generateCode();
        if (redisService.existKey(email)){
            System.out.println("update");
            System.out.println(redisService.getString(email).toString());
            redisService.updateString(email, code);
        }else{
            System.out.println("set");
            redisService.setString(email, code);
        }
        System.out.println(code);
        emailService.sendMail(email, "Verification Code", code);
    }


    @Override
    public boolean register(RegisterForm registerForm) {
        String email = registerForm.getEmail();
        System.out.println(email);
        String redis_code;


        //if there is a corresponding code in redis
        if (redisService.existKey(email)){
            redis_code = redisService.getString(email);

            //compared the code from client and server
            if (registerForm.getVerificationCode().equals(redis_code)){
                userService.registerUser(registerForm);
                return true;
            }
        }else{
            System.out.println("Email not found");
        }
        return false;
    }
}
