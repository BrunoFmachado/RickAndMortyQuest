package com.example.rickandmortyquest.data.remote.api;

import com.example.rickandmortyquest.BuildConfig;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.data.remote.dto.CharacterResponseDto;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class CharacterRemoteDataSource {

    private final HttpRequestExecutor requestExecutor;

    public CharacterRemoteDataSource(HttpRequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void getCharacters(
            int page,
            String status,
            String gender,
            String species,
            ApiCallback<CharacterResponseDto> callback
    ) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BuildConfig.RICK_AND_MORTY_BASE_URL + "character")
                .newBuilder()
                .addQueryParameter("page", String.valueOf(page));

        addQueryIfNotEmpty(urlBuilder, "status", status);
        addQueryIfNotEmpty(urlBuilder, "gender", gender);
        addQueryIfNotEmpty(urlBuilder, "species", species);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        requestExecutor.execute(request, CharacterResponseDto.class, callback);
    }

    private void addQueryIfNotEmpty(HttpUrl.Builder builder, String name, String value) {
        if (value != null && !value.trim().isEmpty()) {
            builder.addQueryParameter(name, value.trim());
        }
    }
}