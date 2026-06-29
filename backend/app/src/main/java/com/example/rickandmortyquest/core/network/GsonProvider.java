package com.example.rickandmortyquest.core.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonProvider {

    private static Gson instance;

    private GsonProvider() {
    }

    public static Gson getGson() {
        if (instance == null) {
            synchronized (GsonProvider.class) {
                if (instance == null) {
                    instance = new GsonBuilder()
                            .serializeNulls()
                            .create();
                }
            }
        }

        return instance;
    }
}