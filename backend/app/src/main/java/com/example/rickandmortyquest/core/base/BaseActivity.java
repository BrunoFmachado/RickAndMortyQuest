package com.example.rickandmortyquest.core.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;

    protected abstract VB inflateBinding();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = inflateBinding();
        setContentView(binding.getRoot());

        setupStatusBar();
        setupViews();
        setupObservers();
        setupListeners();
    }

    protected void setupStatusBar() {
    }

    protected void setupViews() {
    }

    protected void setupObservers() {
    }

    protected void setupListeners() {
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(String message) {
        View root = binding.getRoot();
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    protected void navigateTo(Class<? extends Activity> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    protected void navigateToAndFinish(Class<? extends Activity> targetActivity) {
        startActivity(new Intent(this, targetActivity));
        finish();
    }
}