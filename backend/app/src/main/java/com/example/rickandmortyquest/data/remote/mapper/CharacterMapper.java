package com.example.rickandmortyquest.data.remote.mapper;

import com.example.rickandmortyquest.data.remote.dto.CharacterDto;
import com.example.rickandmortyquest.data.remote.dto.CharacterLocationDto;
import com.example.rickandmortyquest.domain.model.CharacterItem;

import java.util.ArrayList;
import java.util.List;

public final class CharacterMapper {

    private CharacterMapper() {
    }

    public static List<CharacterItem> toDomainList(List<CharacterDto> dtoList) {
        List<CharacterItem> characters = new ArrayList<>();

        if (dtoList == null) {
            return characters;
        }

        for (CharacterDto dto : dtoList) {
            characters.add(toDomain(dto));
        }

        return characters;
    }

    private static CharacterItem toDomain(CharacterDto dto) {
        CharacterLocationDto origin = dto.getOrigin();
        CharacterLocationDto location = dto.getLocation();

        int episodeCount = dto.getEpisode() != null ? dto.getEpisode().size() : 0;

        return new CharacterItem(
                dto.getId(),
                dto.getName(),
                dto.getStatus(),
                dto.getSpecies(),
                dto.getType(),
                dto.getGender(),
                origin != null ? origin.getName() : null,
                location != null ? location.getName() : null,
                dto.getImage(),
                episodeCount,
                dto.getUrl(),
                dto.getCreated()
        );
    }
}