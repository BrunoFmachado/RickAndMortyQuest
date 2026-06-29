package com.example.rickandmortyquest.domain.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.domain.model.AuthUser;

public interface AuthRepository {

    void login(String email, String password, ApiCallback<AuthUser> callback);
}