package com.example.rickandmortyquest.feature.characters.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmortyquest.core.base.BaseViewModel;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.data.remote.api.CharacterPhotoRemoteDataSource;
import com.example.rickandmortyquest.data.remote.dto.PhotoCaptureRequestDto;
import com.example.rickandmortyquest.data.remote.dto.PhotoPostResponseDto;
import com.example.rickandmortyquest.domain.model.CharacterItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CharacterDetailViewModel extends BaseViewModel {

    private final CharacterPhotoRemoteDataSource remoteDataSource;

    private final MutableLiveData<String> capturedImageUri = new MutableLiveData<>();
    private final MutableLiveData<UiState<PhotoPostResponseDto>> postState =
            new MutableLiveData<>(UiState.idle());

    private String pendingCameraUri;

    public CharacterDetailViewModel(CharacterPhotoRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public LiveData<String> getCapturedImageUri() {
        return capturedImageUri;
    }

    public LiveData<UiState<PhotoPostResponseDto>> getPostState() {
        return postState;
    }

    public void setPendingCameraUri(String uri) {
        pendingCameraUri = uri;
    }

    public String getPendingCameraUri() {
        return pendingCameraUri;
    }

    public void onPhotoCaptured(CharacterItem character) {
        if (pendingCameraUri == null || pendingCameraUri.trim().isEmpty()) {
            postState.setValue(UiState.error("Imagem capturada inválida."));
            return;
        }

        capturedImageUri.setValue(pendingCameraUri);
        sendCapturedPhoto(character, pendingCameraUri);
    }

    private void sendCapturedPhoto(CharacterItem character, String imageUri) {
        postState.setValue(UiState.loading());

        PhotoCaptureRequestDto requestDto = new PhotoCaptureRequestDto(
                character.getId(),
                character.getName(),
                imageUri,
                getCurrentDateTime(),
                "camera"
        );

        remoteDataSource.sendCapturedPhoto(requestDto, new ApiCallback<PhotoPostResponseDto>() {
            @Override
            public void onSuccess(PhotoPostResponseDto data) {
                postState.setValue(UiState.success(data));
            }

            @Override
            public void onError(Throwable throwable) {
                postState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
            }
        });
    }

    private String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .format(new Date());
    }
}