package com.example.rickandmortyquest.data.remote.dto;

import java.util.List;

public class CharacterResponseDto {

    private CharacterPageInfoDto info;
    private List<CharacterDto> results;

    public CharacterPageInfoDto getInfo() {
        return info;
    }

    public List<CharacterDto> getResults() {
        return results;
    }
}