package com.example.rickandmortyquest.feature.characters.list;

import android.content.Intent;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmortyquest.R;
import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.ui.ViewUtils;
import com.example.rickandmortyquest.data.remote.api.CharacterRemoteDataSource;
import com.example.rickandmortyquest.data.repository.CharacterRepositoryImpl;
import com.example.rickandmortyquest.databinding.ActivityCharacterListBinding;
import com.example.rickandmortyquest.domain.model.CharacterItem;
import com.example.rickandmortyquest.domain.repository.CharacterRepository;
import com.example.rickandmortyquest.feature.characters.detail.CharacterDetailActivity;

import java.util.List;

public class CharacterListActivity extends BaseActivity<ActivityCharacterListBinding> {

    private CharacterListViewModel viewModel;
    private CharacterAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected ActivityCharacterListBinding inflateBinding() {
        return ActivityCharacterListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        setupViewModel();
        setupRecyclerView();
        setupFilters();
        viewModel.initialize();
    }

    @Override
    protected void setupObservers() {
        viewModel.getCharacterState().observe(this, this::renderCharacterState);
    }

    @Override
    protected void setupListeners() {
        binding.backButton.setOnClickListener(view -> finish());

        binding.statusAutoComplete.setOnItemClickListener((parent, view, position, id) -> applySelectedFilters());
        binding.genderAutoComplete.setOnItemClickListener((parent, view, position, id) -> applySelectedFilters());
        binding.speciesAutoComplete.setOnItemClickListener((parent, view, position, id) -> applySelectedFilters());

        binding.clearFiltersButton.setOnClickListener(view -> {
            binding.statusAutoComplete.setText("Todos", false);
            binding.genderAutoComplete.setText("Todos", false);
            binding.speciesAutoComplete.setText("Todos", false);
            viewModel.clearFilters();
        });

        binding.retryButton.setOnClickListener(view -> viewModel.refresh());
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void setupRecyclerView() {
        adapter = new CharacterAdapter(character -> {
            Intent intent = new Intent(this, CharacterDetailActivity.class);
            intent.putExtra(CharacterDetailActivity.EXTRA_CHARACTER, character);
            startActivity(intent);
        });

        layoutManager = new LinearLayoutManager(this);
        binding.charactersRecyclerView.setLayoutManager(layoutManager);
        binding.charactersRecyclerView.setAdapter(adapter);

        binding.charactersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) {
                    return;
                }

                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                viewModel.loadNextPageIfNeeded(lastVisiblePosition, totalItemCount);
            }
        });
    }

    private void setupViewModel() {
        HttpRequestExecutor executor = new HttpRequestExecutor();
        CharacterRemoteDataSource remoteDataSource = new CharacterRemoteDataSource(executor);
        CharacterRepository repository = new CharacterRepositoryImpl(remoteDataSource);
        CharacterListViewModelFactory factory = new CharacterListViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(CharacterListViewModel.class);
    }

    private void setupFilters() {
        setupDropdown(binding.statusAutoComplete, new String[]{
                "Todos", "Alive", "Dead", "unknown"
        });

        setupDropdown(binding.genderAutoComplete, new String[]{
                "Todos", "Female", "Male", "Genderless", "unknown"
        });

        setupDropdown(binding.speciesAutoComplete, new String[]{
                "Todos", "Human", "Alien", "Humanoid", "Animal", "Robot", "Mythological Creature"
        });

        binding.statusAutoComplete.setText("Todos", false);
        binding.genderAutoComplete.setText("Todos", false);
        binding.speciesAutoComplete.setText("Todos", false);
    }

    private void setupDropdown(com.google.android.material.textfield.MaterialAutoCompleteTextView view, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                items
        );
        view.setAdapter(adapter);
    }

    private void applySelectedFilters() {
        String status = getFilterText(binding.statusAutoComplete.getText());
        String gender = getFilterText(binding.genderAutoComplete.getText());
        String species = getFilterText(binding.speciesAutoComplete.getText());
        viewModel.applyFilters(status, gender, species);
    }

    private void renderCharacterState(UiState<List<CharacterItem>> state) {
        binding.swipeRefreshLayout.setRefreshing(false);

        if (state instanceof UiState.Idle) {
            setLoading(false);
            return;
        }

        if (state instanceof UiState.Loading) {
            setLoading(true);
            showStateContainer(false, "", "");
            return;
        }

        if (state instanceof UiState.Success) {
            setLoading(false);
            UiState.Success<List<CharacterItem>> successState = (UiState.Success<List<CharacterItem>>) state;
            List<CharacterItem> characters = successState.getData();
            adapter.submitList(characters);

            boolean isEmpty = characters == null || characters.isEmpty();
            showStateContainer(isEmpty, "Lista vazia", getString(R.string.character_empty));
            return;
        }

        if (state instanceof UiState.Empty) {
            setLoading(false);
            adapter.submitList(java.util.Collections.emptyList());
            UiState.Empty<List<CharacterItem>> emptyState = (UiState.Empty<List<CharacterItem>>) state;
            showStateContainer(true, "Lista vazia", emptyState.getMessage());
            return;
        }

        if (state instanceof UiState.Error) {
            setLoading(false);
            adapter.submitList(java.util.Collections.emptyList());
            UiState.Error<List<CharacterItem>> errorState = (UiState.Error<List<CharacterItem>>) state;
            showStateContainer(true, "Erro ao carregar", errorState.getMessage());
        }
    }

    private void setLoading(boolean isLoading) {
        ViewUtils.visibleIf(binding.loadingProgressBar, isLoading);
        ViewUtils.visibleIf(binding.swipeRefreshLayout, !isLoading);
    }

    private void showStateContainer(boolean shouldShow, String title, String description) {
        ViewUtils.visibleIf(binding.stateContainer, shouldShow);
        ViewUtils.visibleIf(binding.swipeRefreshLayout, !shouldShow);
        binding.stateTitleTextView.setText(title);
        binding.stateDescriptionTextView.setText(description);
    }

    private String getFilterText(CharSequence value) {
        String text = getText(value).trim();
        return text.equalsIgnoreCase("Todos") ? "" : text;
    }

    private String getText(CharSequence value) {
        return value == null ? "" : value.toString();
    }
}
