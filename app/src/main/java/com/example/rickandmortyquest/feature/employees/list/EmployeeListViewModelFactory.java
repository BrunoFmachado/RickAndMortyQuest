package com.example.rickandmortyquest.feature.employees.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

public class EmployeeListViewModelFactory implements ViewModelProvider.Factory {

    private final EmployeeRepository repository;

    public EmployeeListViewModelFactory(EmployeeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EmployeeListViewModel.class)) {
            return (T) new EmployeeListViewModel(repository);
        }

        throw new IllegalArgumentException("ViewModel desconhecida: " + modelClass.getName());
    }
}