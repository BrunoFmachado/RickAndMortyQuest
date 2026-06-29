package com.example.rickandmortyquest.core.state;

public abstract class UiState<T> {

    protected UiState() {
    }

    public static <T> UiState<T> idle() {
        return new Idle<>();
    }

    public static <T> UiState<T> loading() {
        return new Loading<>();
    }

    public static <T> UiState<T> success(T data) {
        return new Success<>(data);
    }

    public static <T> UiState<T> empty(String message) {
        return new Empty<>(message);
    }

    public static <T> UiState<T> error(String message) {
        return new Error<>(message, null);
    }

    public static <T> UiState<T> error(String message, Throwable throwable) {
        return new Error<>(message, throwable);
    }

    public static final class Idle<T> extends UiState<T> {
    }

    public static final class Loading<T> extends UiState<T> {
    }

    public static final class Success<T> extends UiState<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Empty<T> extends UiState<T> {
        private final String message;

        public Empty(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final class Error<T> extends UiState<T> {
        private final String message;
        private final Throwable throwable;

        public Error(String message, Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
        }

        public String getMessage() {
            return message;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}