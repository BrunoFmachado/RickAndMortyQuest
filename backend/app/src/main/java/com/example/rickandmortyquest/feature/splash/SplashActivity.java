package com.example.rickandmortyquest.feature.splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.databinding.ActivitySplashBinding;
import com.example.rickandmortyquest.feature.home.HomeActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected ActivitySplashBinding inflateBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        // Zera o estado de todos os elementos para a entrada teatral
        binding.portalGlow.setAlpha(0f);
        binding.portalGlow.setScaleX(0.5f);
        binding.portalGlow.setScaleY(0.5f);

        binding.logoTextView.setAlpha(0f);
        binding.logoTextView.setScaleX(0.5f);
        binding.logoTextView.setScaleY(0.5f);

        binding.progressBar.setAlpha(0f);

        startSystemBootAnimation();
    }

    private void startSystemBootAnimation() {
        // 1. Efeito de Pulso Neon do Portal (Loop Infinito)
        ObjectAnimator glowAnimator = ObjectAnimator.ofPropertyValuesHolder(
                binding.portalGlow,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 0.85f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1.1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1.1f)
        );
        glowAnimator.setDuration(1200);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setInterpolator(new DecelerateInterpolator());
        glowAnimator.start();

        // 2. Entrada do Logo com efeito "Elástico" (Overshoot)
        ObjectAnimator logoAlpha = ObjectAnimator.ofFloat(binding.logoTextView, View.ALPHA, 0f, 1f);
        ObjectAnimator logoScaleX = ObjectAnimator.ofFloat(binding.logoTextView, View.SCALE_X, 0.5f, 1f);
        ObjectAnimator logoScaleY = ObjectAnimator.ofFloat(binding.logoTextView, View.SCALE_Y, 0.5f, 1f);

        AnimatorSet logoSet = new AnimatorSet();
        logoSet.playTogether(logoAlpha, logoScaleX, logoScaleY);
        logoSet.setDuration(800);
        logoSet.setInterpolator(new OvershootInterpolator(1.2f)); // Dá um bounce premium
        logoSet.setStartDelay(300);

        AnimatorSet titleSet = new AnimatorSet();
        titleSet.setDuration(600);
        titleSet.setInterpolator(new DecelerateInterpolator());
        titleSet.setStartDelay(700);
        ObjectAnimator progressAlpha = ObjectAnimator.ofFloat(binding.progressBar, View.ALPHA, 0f, 1f);
        progressAlpha.setDuration(500);
        progressAlpha.setStartDelay(1100);
        AnimatorSet mainSet = new AnimatorSet();
        mainSet.playTogether(logoSet, titleSet, progressAlpha);
        mainSet.start();

        new Handler(Looper.getMainLooper()).postDelayed(this::navigateToHome, 2800);
    }

    private void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        // Transição suave de fade entre as telas (dispensa animações nativas duras do Android)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}