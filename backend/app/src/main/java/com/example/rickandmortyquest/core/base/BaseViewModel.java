package com.example.rickandmortyquest.core.base;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    protected String getDefaultErrorMessage(Throwable throwable) {
        if (throwable == null || throwable.getMessage() == null || throwable.getMessage().trim().isEmpty()) {
            return "Não foi possível concluir a operação. Tente novamente.";
        }

        return throwable.getMessage();
    }
}