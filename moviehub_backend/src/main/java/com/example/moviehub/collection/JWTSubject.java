package com.example.moviehub.collection;

public class JWTSubject {
    private Boolean refresh;
    private String email;

    public JWTSubject(boolean refresh, String email) {
        this.refresh = refresh;
        this.email = email;
    }

    public Boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
