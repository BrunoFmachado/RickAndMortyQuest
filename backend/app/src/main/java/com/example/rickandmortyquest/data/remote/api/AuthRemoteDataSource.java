package com.example.rickandmortyquest.data.remote.api;

import com.example.rickandmortyquest.BuildConfig;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.network.GsonProvider;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.data.remote.dto.LoginRequestDto;
import com.example.rickandmortyquest.data.remote.dto.LoginResponseDto;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AuthRemoteDataSource {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final HttpRequestExecutor requestExecutor;

    public AuthRemoteDataSource(HttpRequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void login(String email, String password, ApiCallback<LoginResponseDto> callback) {
        LoginRequestDto requestDto = new LoginRequestDto(email, password);

        String jsonBody = GsonProvider.getGson().toJson(requestDto);

        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(BuildConfig.LOCAL_BACKEND_BASE_URL + "api/auth/login")
                .post(body)
                .build();

        requestExecutor.execute(request, LoginResponseDto.class, callback);
    }
}