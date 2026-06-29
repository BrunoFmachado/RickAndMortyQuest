package com.example.rickandmortyquest.feature.menu;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.session.SessionManager;
import com.example.rickandmortyquest.databinding.ActivityMenuBinding;
import com.example.rickandmortyquest.feature.characters.list.CharacterListActivity;
import com.example.rickandmortyquest.feature.employees.list.EmployeeListActivity;
import com.example.rickandmortyquest.feature.home.HomeActivity;

public class MenuActivity extends BaseActivity<ActivityMenuBinding> {

    private SessionManager sessionManager;

    @Override
    protected ActivityMenuBinding inflateBinding() {
        return ActivityMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        sessionManager = new SessionManager(this);

        String userName = sessionManager.getUserName();
        String userEmail = sessionManager.getUserEmail();

        if (userName != null && !userName.trim().isEmpty()) {
            binding.titleTextView.setText("Olá, " + userName);
        }

        if (userEmail != null && !userEmail.trim().isEmpty()) {
            binding.emailTextView.setText(userEmail + " • Acesso Nível 5");
        }
    }

    @Override
    protected void setupListeners() {
        binding.charactersCard.setOnClickListener(view -> openCharacters());
        binding.openCharactersButton.setOnClickListener(view -> openCharacters());

        binding.employeesCard.setOnClickListener(view -> openEmployees());
        binding.openEmployeesButton.setOnClickListener(view -> openEmployees());

        binding.logoutButton.setOnClickListener(view -> {
            sessionManager.clearSession();
            navigateToAndFinish(HomeActivity.class);
        });
    }

    private void openCharacters() {
        navigateTo(CharacterListActivity.class);
    }

    private void openEmployees() {
        navigateTo(EmployeeListActivity.class);
    }
}