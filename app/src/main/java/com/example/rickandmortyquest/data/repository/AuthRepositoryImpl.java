package com.example.rickandmortyquest.data.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.data.remote.api.AuthRemoteDataSource;
import com.example.rickandmortyquest.data.remote.dto.LoginResponseDto;
import com.example.rickandmortyquest.data.remote.mapper.AuthMapper;
import com.example.rickandmortyquest.domain.model.AuthUser;
import com.example.rickandmortyquest.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthRemoteDataSource remoteDataSource;

    public AuthRepositoryImpl(AuthRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void login(String email, String password, ApiCallback<AuthUser> callback) {
        remoteDataSource.login(email, password, new ApiCallback<LoginResponseDto>() {
            @Override
            public void onSuccess(LoginResponseDto data) {
                try {
                    callback.onSuccess(AuthMapper.toDomain(data));
                } catch (Exception exception) {
                    callback.onError(exception);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}