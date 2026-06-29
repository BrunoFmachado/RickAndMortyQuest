package com.example.rickandmortyquest.core.ui;

import android.view.View;

public final class ViewUtils {

    private ViewUtils() {
    }

    public static void visible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void invisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void gone(View view) {
        view.setVisibility(View.GONE);
    }

    public static void visibleIf(View view, boolean condition) {
        view.setVisibility(condition ? View.VISIBLE : View.GONE);
    }

    public static void enabledIf(View view, boolean condition) {
        view.setEnabled(condition);
        view.setAlpha(condition ? 1f : 0.56f);
    }
}