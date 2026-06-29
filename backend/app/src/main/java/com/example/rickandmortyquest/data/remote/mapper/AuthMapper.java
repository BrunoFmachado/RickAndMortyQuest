package com.example.rickandmortyquest.data.remote.mapper;

import com.example.rickandmortyquest.data.remote.dto.LoginResponseDto;
import com.example.rickandmortyquest.data.remote.dto.UserDto;
import com.example.rickandmortyquest.domain.model.AuthUser;

public final class AuthMapper {

    private AuthMapper() {
    }

    public static AuthUser toDomain(LoginResponseDto response) {
        if (response == null || response.getUser() == null) {
            throw new IllegalArgumentException("Resposta de login inválida.");
        }

        UserDto user = response.getUser();

        return new AuthUser(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                response.getToken()
        );
    }
}