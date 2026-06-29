package com.example.rickandmortyquest.feature.characters.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rickandmortyquest.BuildConfig;
import com.example.rickandmortyquest.R;
import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.ui.ViewUtils;
import com.example.rickandmortyquest.data.remote.api.CharacterPhotoRemoteDataSource;
import com.example.rickandmortyquest.data.remote.dto.PhotoPostResponseDto;
import com.example.rickandmortyquest.databinding.ActivityCharacterDetailBinding;
import com.example.rickandmortyquest.domain.model.CharacterItem;

import java.io.File;

public class CharacterDetailActivity extends BaseActivity<ActivityCharacterDetailBinding> {

    public static final String EXTRA_CHARACTER = "extra_character";

    private CharacterDetailViewModel viewModel;
    private CharacterItem character;

    private ActivityResultLauncher<String> permissionLauncher;
    private ActivityResultLauncher<Uri> takePictureLauncher;

    @Override
    protected ActivityCharacterDetailBinding inflateBinding() {
        return ActivityCharacterDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        character = getCharacterFromIntent();

        if (character == null) {
            showSnackbar("Personagem inválido.");
            finish();
            return;
        }

        setupViewModel();
        setupActivityResultLaunchers();
        renderCharacter(character);
    }

    @Override
    protected void setupObservers() {
        viewModel.getCapturedImageUri().observe(this, uri -> {
            if (uri != null && !uri.trim().isEmpty()) {
                loadImage(uri);
                showSnackbar(getString(R.string.photo_updated_success));
            }
        });

        viewModel.getPostState().observe(this, this::renderPostState);
    }

    @Override
    protected void setupListeners() {
        binding.updatePhotoButton.setOnClickListener(view ->
                requestCameraOrOpen()
        );
    }

    private void setupViewModel() {
        HttpRequestExecutor executor = new HttpRequestExecutor();
        CharacterPhotoRemoteDataSource remoteDataSource = new CharacterPhotoRemoteDataSource(executor);
        CharacterDetailViewModelFactory factory = new CharacterDetailViewModelFactory(remoteDataSource);

        viewModel = new ViewModelProvider(this, factory).get(CharacterDetailViewModel.class);
    }

    private void setupActivityResultLaunchers() {
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (Boolean.TRUE.equals(isGranted)) {
                        openNativeCamera();
                    } else {
                        showSnackbar(getString(R.string.camera_permission_denied));
                    }
                }
        );

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (Boolean.TRUE.equals(success)) {
                        viewModel.onPhotoCaptured(character);
                    } else {
                        showSnackbar("Captura cancelada.");
                    }
                }
        );
    }

    private void requestCameraOrOpen() {
        boolean hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;

        if (hasPermission) {
            openNativeCamera();
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openNativeCamera() {
        try {
            Uri photoUri = createCameraImageUri();
            viewModel.setPendingCameraUri(photoUri.toString());
            takePictureLauncher.launch(photoUri);
        } catch (IllegalArgumentException e) {
            showSnackbar("Erro de configuração no FileProvider.");
        } catch (Exception exception) {
            showSnackbar("Não foi possível inicializar a câmera.");
        }
    }

    private Uri createCameraImageUri() throws Exception {
        // Usa a pasta de cache externa se disponível, caso contrário, usa a interna
        File directory = new File(getExternalCacheDir() != null ? getExternalCacheDir() : getCacheDir(), "camera");

        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException("Não foi possível criar o diretório da câmera.");
        }

        // Adiciona um timestamp para garantir que o nome do arquivo seja sempre único
        String timestamp = String.valueOf(System.currentTimeMillis());
        File photoFile = File.createTempFile(
                "character_" + character.getId() + "_" + timestamp,
                ".jpg",
                directory
        );

        return FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                photoFile
        );
    }

    private void renderCharacter(CharacterItem character) {
        binding.nameTextView.setText(character.getName());

        binding.statusChip.setText(character.getStatus());
        binding.speciesChip.setText(character.getSpecies());
        binding.genderChip.setText(character.getGender());

        binding.infoOriginTextView.setText(character.getOriginName());

        loadInitialImage(character.getImageUrl());
    }

    private void loadInitialImage(String imageSource) {
        Glide.with(this)
                .load(imageSource)
                .centerCrop()
                .into(binding.characterImageView);
    }

    private void loadImage(String imageSource) {
        Glide.with(this)
                .load(imageSource)
                .centerCrop()
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.characterImageView);
    }
    private void renderPostState(UiState<PhotoPostResponseDto> state) {
        if (state instanceof UiState.Loading) {
            ViewUtils.enabledIf(binding.updatePhotoButton, false);
            return;
        }

        ViewUtils.enabledIf(binding.updatePhotoButton, true);

        if (state instanceof UiState.Success) {
            showSnackbar(getString(R.string.photo_post_success));
            return;
        }

        if (state instanceof UiState.Error) {
            showSnackbar(getString(R.string.photo_post_error));
        }
    }

    private CharacterItem getCharacterFromIntent() {
        Intent intent = getIntent();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            return intent.getSerializableExtra(EXTRA_CHARACTER, CharacterItem.class);
        }

        Object serializable = intent.getSerializableExtra(EXTRA_CHARACTER);

        if (serializable instanceof CharacterItem) {
            return (CharacterItem) serializable;
        }

        return null;
    }
}