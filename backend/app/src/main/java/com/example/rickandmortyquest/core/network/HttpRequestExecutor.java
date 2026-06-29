package com.example.rickandmortyquest.core.network;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpRequestExecutor {

    private final OkHttpClient client;
    private final Gson gson;
    private final Handler mainHandler;

    public HttpRequestExecutor() {
        this.client = HttpClientProvider.getClient();
        this.gson = GsonProvider.getGson();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public <T> void execute(Request request, Class<T> responseClass, ApiCallback<T> callback) {
        executeInternal(request, responseClass, callback);
    }

    public <T> void execute(Request request, Type responseType, ApiCallback<T> callback) {
        executeInternal(request, responseType, callback);
    }

    private <T> void executeInternal(Request request, Object targetType, ApiCallback<T> callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException exception) {
                postError(callback, exception);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = extractBody(response);

                    if (!response.isSuccessful()) {
                        postError(callback, new NetworkException(response.code(), responseBody));
                        return;
                    }

                    T parsedResponse = parseResponse(responseBody, targetType);
                    postSuccess(callback, parsedResponse);
                } catch (Exception exception) {
                    postError(callback, exception);
                } finally {
                    response.close();
                }
            }
        });
    }

    private String extractBody(Response response) throws IOException {
        ResponseBody body = response.body();
        return body != null ? body.string() : "";
    }

    @SuppressWarnings("unchecked")
    private <T> T parseResponse(String responseBody, Object targetType) {
        if (targetType == Void.class) {
            return null;
        }

        if (targetType instanceof Class<?>) {
            return gson.fromJson(responseBody, (Class<T>) targetType);
        }

        if (targetType instanceof Type) {
            return gson.fromJson(responseBody, (Type) targetType);
        }

        throw new IllegalArgumentException("Tipo de resposta inválido.");
    }

    private <T> void postSuccess(ApiCallback<T> callback, T data) {
        mainHandler.post(() -> callback.onSuccess(data));
    }

    private <T> void postError(ApiCallback<T> callback, Throwable throwable) {
        mainHandler.post(() -> callback.onError(throwable));
    }
}