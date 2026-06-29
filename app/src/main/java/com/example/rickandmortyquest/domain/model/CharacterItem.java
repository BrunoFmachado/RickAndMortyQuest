package com.example.rickandmortyquest.domain.model;

import java.io.Serializable;

public class CharacterItem implements Serializable {

    private final int id;
    private final String name;
    private final String status;
    private final String species;
    private final String type;
    private final String gender;
    private final String originName;
    private final String locationName;
    private final String imageUrl;
    private final int episodeCount;
    private final String apiUrl;
    private final String createdAt;

    public CharacterItem(
            int id,
            String name,
            String status,
            String species,
            String type,
            String gender,
            String originName,
            String locationName,
            String imageUrl,
            int episodeCount,
            String apiUrl,
            String createdAt
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.originName = originName;
        this.locationName = locationName;
        this.imageUrl = imageUrl;
        this.episodeCount = episodeCount;
        this.apiUrl = apiUrl;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return valueOrDash(name);
    }

    public String getStatus() {
        return valueOrDash(status);
    }

    public String getSpecies() {
        return valueOrDash(species);
    }

    public String getType() {
        return valueOrDash(type);
    }

    public String getGender() {
        return valueOrDash(gender);
    }

    public String getOriginName() {
        return valueOrDash(originName);
    }

    public String getLocationName() {
        return valueOrDash(locationName);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public String getApiUrl() {
        return valueOrDash(apiUrl);
    }

    public String getCreatedAt() {
        return valueOrDash(createdAt);
    }

    private String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}