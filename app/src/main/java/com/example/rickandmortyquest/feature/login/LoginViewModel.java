package com.example.rickandmortyquest.feature.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmortyquest.core.base.BaseViewModel;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.session.SessionManager;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.utils.Event;
import com.example.rickandmortyquest.domain.model.AuthUser;
import com.example.rickandmortyquest.domain.repository.AuthRepository;

public class LoginViewModel extends BaseViewModel {

    private final AuthRepository authRepository;
    private final SessionManager sessionManager;

    private final MutableLiveData<UiState<AuthUser>> loginState = new MutableLiveData<>(UiState.idle());
    private final MutableLiveData<Event<Boolean>> navigateToMenuEvent = new MutableLiveData<>();

    public LoginViewModel(AuthRepository authRepository, SessionManager sessionManager) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManager;
    }

    public LiveData<UiState<AuthUser>> getLoginState() {
        return loginState;
    }

    public LiveData<Event<Boolean>> getNavigateToMenuEvent() {
        return navigateToMenuEvent;
    }

    public void login(String email, String password) {
        String normalizedEmail = email == null ? "" : email.trim();
        String normalizedPassword = password == null ? "" : password.trim();

        if (normalizedEmail.isEmpty()) {
            loginState.setValue(UiState.error("Informe o e-mail."));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(normalizedEmail).matches()) {
            loginState.setValue(UiState.error("Informe um e-mail válido."));
            return;
        }

        if (normalizedPassword.isEmpty()) {
            loginState.setValue(UiState.error("Informe a senha."));
            return;
        }

        loginState.setValue(UiState.loading());

        authRepository.login(normalizedEmail, normalizedPassword, new ApiCallback<AuthUser>() {
            @Override
            public void onSuccess(AuthUser user) {
                sessionManager.saveSession(
                        user.getId(),
                        user.getToken(),
                        user.getName(),
                        user.getEmail()
                );

                loginState.setValue(UiState.success(user));
                navigateToMenuEvent.setValue(new Event<>(true));
            }

            @Override
            public void onError(Throwable throwable) {
                loginState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
            }
        });
    }
}