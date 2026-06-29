package com.example.rickandmortyquest.feature.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.core.session.SessionManager;
import com.example.rickandmortyquest.domain.repository.AuthRepository;
import com.example.rickandmortyquest.feature.login.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final AuthRepository authRepository;
    private final SessionManager sessionManager;

    public LoginViewModelFactory(AuthRepository authRepository, SessionManager sessionManager) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(authRepository, sessionManager);
        }

        throw new IllegalArgumentException("ViewModel desconhecida: " + modelClass.getName());
    }
}