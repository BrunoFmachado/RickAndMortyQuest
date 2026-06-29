package com.example.rickandmortyquest.data.remote.api;

import com.example.rickandmortyquest.BuildConfig;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.network.GsonProvider;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.data.remote.dto.PhotoCaptureRequestDto;
import com.example.rickandmortyquest.data.remote.dto.PhotoPostResponseDto;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CharacterPhotoRemoteDataSource {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final HttpRequestExecutor requestExecutor;

    public CharacterPhotoRemoteDataSource(HttpRequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void sendCapturedPhoto(
            PhotoCaptureRequestDto requestDto,
            ApiCallback<PhotoPostResponseDto> callback
    ) {
        String jsonBody = GsonProvider.getGson().toJson(requestDto);

        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(BuildConfig.FAKE_POST_BASE_URL + "posts")
                .post(body)
                .build();

        requestExecutor.execute(request, PhotoPostResponseDto.class, callback);
    }
}