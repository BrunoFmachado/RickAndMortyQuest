package com.example.rickandmortyquest.feature.characters.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.domain.repository.CharacterRepository;

public class CharacterListViewModelFactory implements ViewModelProvider.Factory {

    private final CharacterRepository repository;

    public CharacterListViewModelFactory(CharacterRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CharacterListViewModel.class)) {
            return (T) new CharacterListViewModel(repository);
        }

        throw new IllegalArgumentException("ViewModel desconhecida: " + modelClass.getName());
    }
}