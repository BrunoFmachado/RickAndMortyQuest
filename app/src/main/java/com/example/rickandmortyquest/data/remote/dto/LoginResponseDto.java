package com.example.rickandmortyquest.data.remote.dto;

public class LoginResponseDto {

    private boolean success;
    private String token;
    private UserDto user;

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}