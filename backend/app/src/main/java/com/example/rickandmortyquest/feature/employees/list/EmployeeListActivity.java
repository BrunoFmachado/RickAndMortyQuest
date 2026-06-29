package com.example.rickandmortyquest.feature.employees.list;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.ui.ViewUtils;
import com.example.rickandmortyquest.data.remote.api.EmployeeRemoteDataSource;
import com.example.rickandmortyquest.data.repository.EmployeeRepositoryImpl;
import com.example.rickandmortyquest.databinding.ActivityEmployeeListBinding;
import com.example.rickandmortyquest.domain.model.Employee;
import com.example.rickandmortyquest.domain.repository.EmployeeRepository;
import com.example.rickandmortyquest.feature.employees.form.EmployeeAdapter;
import com.example.rickandmortyquest.feature.employees.form.EmployeeFormActivity;

import java.util.Collections;
import java.util.List;

public class EmployeeListActivity extends BaseActivity<ActivityEmployeeListBinding> {

    private EmployeeListViewModel viewModel;
    private EmployeeAdapter adapter;

    @Override
    protected ActivityEmployeeListBinding inflateBinding() {
        return ActivityEmployeeListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        setupViewModel();
        setupRecyclerView();

        viewModel.loadEmployees();
    }

    @Override
    protected void setupObservers() {
        viewModel.getEmployeeState().observe(this, this::renderEmployeeState);

        viewModel.getMessageEvent().observe(this, event -> {
            String message = event.getContentIfNotHandled();

            if (message != null && !message.trim().isEmpty()) {
                showSnackbar(message);
            }
        });
    }

    @Override
    protected void setupListeners() {
        binding.addEmployeeButton.setOnClickListener(view ->
                startActivity(new Intent(this, EmployeeFormActivity.class))
        );

        binding.retryButton.setOnClickListener(view ->
                viewModel.loadEmployees()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (viewModel != null) {
            viewModel.loadEmployees();
        }
    }

    private void setupViewModel() {
        HttpRequestExecutor executor = new HttpRequestExecutor();
        EmployeeRemoteDataSource remoteDataSource = new EmployeeRemoteDataSource(executor);
        EmployeeRepository repository = new EmployeeRepositoryImpl(remoteDataSource);
        EmployeeListViewModelFactory factory = new EmployeeListViewModelFactory(repository);

        viewModel = new ViewModelProvider(this, factory).get(EmployeeListViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new EmployeeAdapter(new EmployeeAdapter.OnEmployeeActionListener() {
            @Override
            public void onEdit(Employee employee) {
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeFormActivity.class);
                intent.putExtra(EmployeeFormActivity.EXTRA_EMPLOYEE, employee);
                startActivity(intent);
            }

            @Override
            public void onDelete(Employee employee) {
                viewModel.deleteEmployee(employee);
            }
        });

        binding.employeesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.employeesRecyclerView.setAdapter(adapter);
    }

    private void renderEmployeeState(UiState<List<Employee>> state) {
        if (state instanceof UiState.Idle) {
            setLoading(false);
            return;
        }

        if (state instanceof UiState.Loading) {
            setLoading(true);
            showStateContainer(false, "", "", false);
            return;
        }

        if (state instanceof UiState.Success) {
            setLoading(false);

            UiState.Success<List<Employee>> successState = (UiState.Success<List<Employee>>) state;
            List<Employee> employees = successState.getData();

            adapter.submitList(employees);

            boolean isEmpty = employees == null || employees.isEmpty();
            showStateContainer(
                    isEmpty,
                    "Lista vazia",
                    "Nenhum funcionário cadastrado.",
                    false
            );

            return;
        }

        if (state instanceof UiState.Empty) {
            setLoading(false);
            adapter.submitList(Collections.emptyList());

            UiState.Empty<List<Employee>> emptyState = (UiState.Empty<List<Employee>>) state;
            showStateContainer(true, "Lista vazia", emptyState.getMessage(), false);
            return;
        }

        if (state instanceof UiState.Error) {
            setLoading(false);
            adapter.submitList(Collections.emptyList());

            UiState.Error<List<Employee>> errorState = (UiState.Error<List<Employee>>) state;
            showStateContainer(true, "Erro ao carregar", errorState.getMessage(), true);
        }
    }

    private void setLoading(boolean isLoading) {
        ViewUtils.visibleIf(binding.loadingProgressBar, isLoading);
        ViewUtils.visibleIf(binding.employeesRecyclerView, !isLoading);
    }

    private void showStateContainer(
            boolean shouldShow,
            String title,
            String description,
            boolean shouldShowRetry
    ) {
        ViewUtils.visibleIf(binding.stateContainer, shouldShow);
        ViewUtils.visibleIf(binding.retryButton, shouldShowRetry);

        binding.stateTitleTextView.setText(title);
        binding.stateDescriptionTextView.setText(description);
    }
}