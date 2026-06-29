package com.example.rickandmortyquest.feature.auth;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.core.session.SessionManager;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.ui.ViewUtils;
import com.example.rickandmortyquest.data.remote.api.AuthRemoteDataSource;
import com.example.rickandmortyquest.data.repository.AuthRepositoryImpl;
import com.example.rickandmortyquest.databinding.ActivityLoginBinding;
import com.example.rickandmortyquest.domain.model.AuthUser;
import com.example.rickandmortyquest.domain.repository.AuthRepository;
import com.example.rickandmortyquest.feature.login.LoginViewModel;
import com.example.rickandmortyquest.feature.menu.MenuActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private LoginViewModel viewModel;

    @Override
    protected ActivityLoginBinding inflateBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        setupViewModel();
    }

    @Override
    protected void setupObservers() {
        viewModel.getLoginState().observe(this, this::renderLoginState);

        viewModel.getNavigateToMenuEvent().observe(this, event -> {
            Boolean shouldNavigate = event.getContentIfNotHandled();

            if (Boolean.TRUE.equals(shouldNavigate)) {
                startActivity(new Intent(this, MenuActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void setupListeners() {
        binding.loginButton.setOnClickListener(view -> {
            clearInputErrors();

            String email = binding.emailEditText.getText() != null
                    ? binding.emailEditText.getText().toString()
                    : "";

            String password = binding.passwordEditText.getText() != null
                    ? binding.passwordEditText.getText().toString()
                    : "";

            viewModel.login(email, password);
        });
    }

    private void setupViewModel() {
        HttpRequestExecutor executor = new HttpRequestExecutor();
        AuthRemoteDataSource remoteDataSource = new AuthRemoteDataSource(executor);
        AuthRepository repository = new AuthRepositoryImpl(remoteDataSource);
        SessionManager sessionManager = new SessionManager(this);

        LoginViewModelFactory factory = new LoginViewModelFactory(repository, sessionManager);

        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    private void renderLoginState(UiState<AuthUser> state) {
        if (state instanceof UiState.Idle) {
            setLoading(false);
            return;
        }

        if (state instanceof UiState.Loading) {
            setLoading(true);
            return;
        }

        if (state instanceof UiState.Success) {
            setLoading(false);
            showSnackbar("Login realizado com sucesso.");
            return;
        }

        if (state instanceof UiState.Error) {
            setLoading(false);

            UiState.Error<AuthUser> errorState = (UiState.Error<AuthUser>) state;
            showSnackbar(errorState.getMessage());

            applyFieldErrorIfNeeded(errorState.getMessage());
        }
    }

    private void setLoading(boolean isLoading) {
        ViewUtils.enabledIf(binding.loginButton, !isLoading);
        ViewUtils.enabledIf(binding.emailEditText, !isLoading);
        ViewUtils.enabledIf(binding.passwordEditText, !isLoading);
    }

    private void clearInputErrors() {
        binding.emailInputLayout.setError(null);
        binding.passwordInputLayout.setError(null);
    }

    private void applyFieldErrorIfNeeded(String message) {
        if ("Informe o e-mail.".equals(message) || "Informe um e-mail válido.".equals(message)) {
            binding.emailInputLayout.setError(message);
            return;
        }

        if ("Informe a senha.".equals(message)) {
            binding.passwordInputLayout.setError(message);
        }
    }
}