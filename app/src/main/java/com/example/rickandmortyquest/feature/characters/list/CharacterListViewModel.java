package com.example.rickandmortyquest.feature.characters.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmortyquest.core.base.BaseViewModel;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.domain.model.CharacterItem;
import com.example.rickandmortyquest.domain.repository.CharacterRepository;

import java.util.ArrayList;
import java.util.List;

public class CharacterListViewModel extends BaseViewModel {

    private static final int FIRST_PAGE = 1;
    private static final int MAX_PAGE = 3;
    private static final int LOAD_MORE_THRESHOLD = 5;

    private final CharacterRepository repository;

    private final MutableLiveData<UiState<List<CharacterItem>>> characterState =
            new MutableLiveData<>(UiState.idle());

    private final List<CharacterItem> loadedCharacters = new ArrayList<>();

    private int currentPage = FIRST_PAGE;
    private boolean isLoading = false;
    private boolean reachedPageLimit = false;

    private String currentStatus = "";
    private String currentGender = "";
    private String currentSpecies = "";

    public CharacterListViewModel(CharacterRepository repository) {
        this.repository = repository;
    }

    public LiveData<UiState<List<CharacterItem>>> getCharacterState() {
        return characterState;
    }

    public void initialize() {
        if (loadedCharacters.isEmpty()) {
            loadFirstPage();
        } else {
            characterState.setValue(UiState.success(new ArrayList<>(loadedCharacters)));
        }
    }

    public void refresh() {
        loadFirstPage();
    }

    public void applyFilters(String status, String gender, String species) {
        currentStatus = normalizeFilter(status);
        currentGender = normalizeFilter(gender);
        currentSpecies = normalizeFilter(species);

        loadFirstPage();
    }

    public void clearFilters() {
        currentStatus = "";
        currentGender = "";
        currentSpecies = "";

        loadFirstPage();
    }

    public void loadNextPageIfNeeded(int lastVisibleItemPosition, int totalItemCount) {
        boolean shouldLoadMore = totalItemCount > 0
                && lastVisibleItemPosition >= totalItemCount - LOAD_MORE_THRESHOLD;

        if (shouldLoadMore) {
            loadNextPage();
        }
    }

    private void loadFirstPage() {
        currentPage = FIRST_PAGE;
        reachedPageLimit = false;
        loadedCharacters.clear();

        loadCharacters(currentPage, true);
    }

    private void loadNextPage() {
        if (isLoading || reachedPageLimit) {
            return;
        }

        if (currentPage >= MAX_PAGE) {
            reachedPageLimit = true;
            return;
        }

        loadCharacters(currentPage + 1, false);
    }

    private void loadCharacters(int page, boolean clearPrevious) {
        if (isLoading) {
            return;
        }

        isLoading = true;

        if (clearPrevious) {
            characterState.setValue(UiState.loading());
        }

        repository.getCharacters(page, currentStatus, currentGender, currentSpecies, new ApiCallback<List<CharacterItem>>() {
            @Override
            public void onSuccess(List<CharacterItem> data) {
                isLoading = false;
                currentPage = page;

                if (data == null || data.isEmpty()) {
                    if (loadedCharacters.isEmpty()) {
                        characterState.setValue(UiState.empty("Nenhum personagem encontrado."));
                    } else {
                        characterState.setValue(UiState.success(new ArrayList<>(loadedCharacters)));
                    }
                    return;
                }

                loadedCharacters.addAll(data);

                if (currentPage >= MAX_PAGE) {
                    reachedPageLimit = true;
                }

                characterState.setValue(UiState.success(new ArrayList<>(loadedCharacters)));
            }

            @Override
            public void onError(Throwable throwable) {
                isLoading = false;

                if (loadedCharacters.isEmpty()) {
                    characterState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
                } else {
                    characterState.setValue(UiState.success(new ArrayList<>(loadedCharacters)));
                }
            }
        });
    }

    private String normalizeFilter(String value) {
        if (value == null) {
            return "";
        }

        String normalized = value.trim();

        if (normalized.equalsIgnoreCase("Todos") || normalized.equalsIgnoreCase("Todas")) {
            return "";
        }

        return normalized;
    }
}