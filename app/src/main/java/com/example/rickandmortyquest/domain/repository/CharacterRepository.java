package com.example.rickandmortyquest.domain.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.domain.model.CharacterItem;

import java.util.List;

public interface CharacterRepository {

    void getCharacters(
            int page,
            String status,
            String gender,
            String species,
            ApiCallback<List<CharacterItem>> callback
    );
}