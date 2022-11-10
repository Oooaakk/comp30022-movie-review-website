package com.example.moviehub.collection;

public class RegisterForm {

    private String email;
    private String password;
    private String verificationCode;

    private Integer age;
    private String gender;
    private String username;
    private String dob;


    public RegisterForm(String email, String password, String verificationCode, Integer age, String gender, String username, String dob) {
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.dob = dob;
    }

    public RegisterForm() {
    }

    public RegisterForm(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
