package com.example.rickandmortyquest.data.remote.dto;

public class PhotoCaptureRequestDto {

    private final int characterId;
    private final String characterName;
    private final String capturedImageUri;
    private final String capturedAt;
    private final String source;

    public PhotoCaptureRequestDto(
            int characterId,
            String characterName,
            String capturedImageUri,
            String capturedAt,
            String source
    ) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.capturedImageUri = capturedImageUri;
        this.capturedAt = capturedAt;
        this.source = source;
    }

    public int getCharacterId() {
        return characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCapturedImageUri() {
        return capturedImageUri;
    }

    public String getCapturedAt() {
        return capturedAt;
    }

    public String getSource() {
        return source;
    }
}