package com.example.rickandmortyquest.core.network;

import com.example.rickandmortyquest.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public final class HttpClientProvider {

    private static OkHttpClient instance;

    private HttpClientProvider() {
    }

    public static OkHttpClient getClient() {
        if (instance == null) {
            synchronized (HttpClientProvider.class) {
                if (instance == null) {
                    instance = createClient();
                }
            }
        }

        return instance;
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }
}