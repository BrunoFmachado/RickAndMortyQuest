package com.example.rickandmortyquest.feature.characters.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.data.remote.api.CharacterPhotoRemoteDataSource;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {

    private final CharacterPhotoRemoteDataSource remoteDataSource;

    public CharacterDetailViewModelFactory(CharacterPhotoRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CharacterDetailViewModel.class) {
            return (T) new CharacterDetailViewModel(remoteDataSource);
        }

        throw new IllegalArgumentException("ViewModel desconhecida: " + modelClass.getName());
    }
}