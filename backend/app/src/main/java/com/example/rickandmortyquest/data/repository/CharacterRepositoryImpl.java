package com.example.rickandmortyquest.data.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.data.remote.api.CharacterRemoteDataSource;
import com.example.rickandmortyquest.data.remote.dto.CharacterResponseDto;
import com.example.rickandmortyquest.data.remote.mapper.CharacterMapper;
import com.example.rickandmortyquest.domain.model.CharacterItem;
import com.example.rickandmortyquest.domain.repository.CharacterRepository;

import java.util.List;

public class CharacterRepositoryImpl implements CharacterRepository {

    private final CharacterRemoteDataSource remoteDataSource;

    public CharacterRepositoryImpl(CharacterRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getCharacters(
            int page,
            String status,
            String gender,
            String species,
            ApiCallback<List<CharacterItem>> callback
    ) {
        remoteDataSource.getCharacters(page, status, gender, species, new ApiCallback<CharacterResponseDto>() {
            @Override
            public void onSuccess(CharacterResponseDto data) {
                if (data == null || data.getResults() == null) {
                    callback.onSuccess(java.util.Collections.emptyList());
                    return;
                }

                callback.onSuccess(CharacterMapper.toDomainList(data.getResults()));
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
}