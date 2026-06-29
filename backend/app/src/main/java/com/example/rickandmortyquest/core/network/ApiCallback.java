package com.example.rickandmortyquest.core.network;

public interface ApiCallback<T> {

    void onSuccess(T data);

    void onError(Throwable throwable);
}