package com.example.rickandmortyquest.feature.employees.form;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

public class EmployeeFormViewModelFactory implements ViewModelProvider.Factory {

    private final EmployeeRepository repository;

    public EmployeeFormViewModelFactory(EmployeeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EmployeeFormViewModel.class)) {
            return (T) new EmployeeFormViewModel(repository);
        }

        throw new IllegalArgumentException("ViewModel desconhecida: " + modelClass.getName());
    }
}