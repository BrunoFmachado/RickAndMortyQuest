package com.example.rickandmortyquest.feature.home;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.databinding.ActivityHomeBinding;
import com.example.rickandmortyquest.feature.auth.LoginActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    @Override
    protected ActivityHomeBinding inflateBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupListeners() {
        binding.startButton.setOnClickListener(view ->
                navigateTo(LoginActivity.class)
        );
    }
}