package com.example.rickandmortyquest.data.remote.dto;

import java.util.List;

public class CharacterDto {

    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private CharacterLocationDto origin;
    private CharacterLocationDto location;
    private String image;
    private List<String> episode;
    private String url;
    private String created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public CharacterLocationDto getOrigin() {
        return origin;
    }

    public CharacterLocationDto getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }
}