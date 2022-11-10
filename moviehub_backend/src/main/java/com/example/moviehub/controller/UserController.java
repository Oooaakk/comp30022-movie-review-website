package com.example.moviehub.controller;


import com.example.moviehub.collection.ForgotPasswrodForm;
import com.example.moviehub.collection.RegisterForm;
import com.example.moviehub.collection.User;
import com.example.moviehub.service.Impl.RegisterServiceImpl;
import com.example.moviehub.service.Impl.UserServiceImpl;
import com.example.moviehub.util.JWTtokenUtil;
import com.example.moviehub.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;
    private final RegisterServiceImpl registerService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping
    public String save(@RequestBody User user){
        return userService.save(user).getId();
    }

    @PutMapping
    public String update(@RequestBody User user){
        return userService.save(user).getId();
    }

    @PostMapping(value = "/email")
    public ResponseEntity<String> verifyEmail(@RequestBody RegisterForm registerForm) {
        System.out.println(registerForm.getEmail());
        try{
            registerService.sendEmailVerificationCode(registerForm.getEmail());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(JsonUtil.toJsonString(e.getMessage()));
        }

        return ResponseEntity.ok().body(JsonUtil.toJsonString("Email Sent"));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm registerForm){


        if (registerService.register(registerForm)){

            return ResponseEntity.ok().body(JsonUtil.toJsonString("Register Succeeded"));
        }else{

            return ResponseEntity.badRequest().body(JsonUtil.toJsonString("Register Failed"));
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user) {
        System.out.println(user);

        if (userService.loginUser(user)){

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());



            return ResponseEntity.ok(JsonUtil.toJsonString("Login Succeed", JWTtokenUtil.generateToken(user)));
        }else{
            return ResponseEntity.badRequest().body(JsonUtil.toJsonString("Login failed"));
        }

    }

    @PostMapping(value = "/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody ForgotPasswrodForm form){
        System.out.println(form.toString());
        if (userService.forgotPass(form)){
            return ResponseEntity.ok(JsonUtil.toJsonString("Reset Succeed"));
        }else {
            return ResponseEntity.ok(JsonUtil.toJsonString("Reset Failed"));
        }
    }

    @PostMapping(value = "/forgotPassword/email")
    public ResponseEntity resetPassEmail(@RequestBody ForgotPasswrodForm form){
        System.out.println(form.getEmail());
        try{
            userService.sendEmailVerificationCode(form.getEmail());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(JsonUtil.toJsonString(e.getMessage()));
        }

        return ResponseEntity.ok().body(JsonUtil.toJsonString("Email Sent"));

    }



}
