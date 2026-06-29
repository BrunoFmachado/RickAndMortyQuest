package com.example.rickandmortyquest.core.network;

public class NetworkException extends Exception {

    private final int statusCode;
    private final String responseBody;

    public NetworkException(int statusCode, String responseBody) {
        super(createMessage(statusCode, responseBody));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    private static String createMessage(int statusCode, String responseBody) {
        if (statusCode == 401) {
            return "Credenciais inválidas.";
        }

        if (statusCode == 404) {
            return "Recurso não encontrado.";
        }

        if (statusCode >= 500) {
            return "Serviço indisponível no momento.";
        }

        if (responseBody != null && !responseBody.trim().isEmpty()) {
            return responseBody;
        }

        return "Erro de comunicação. Código: " + statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}