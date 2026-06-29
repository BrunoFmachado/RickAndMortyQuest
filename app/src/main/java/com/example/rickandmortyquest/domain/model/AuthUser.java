package com.example.rickandmortyquest.domain.model;

public class AuthUser {

    private final long id;
    private final String name;
    private final String email;
    private final String token;

    public AuthUser(long id, String name, String email, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}