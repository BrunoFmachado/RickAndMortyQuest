package com.example.rickandmortyquest.feature.employees.form;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmortyquest.core.base.BaseActivity;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.ui.ViewUtils;
import com.example.rickandmortyquest.data.remote.api.EmployeeRemoteDataSource;
import com.example.rickandmortyquest.data.repository.EmployeeRepositoryImpl;
import com.example.rickandmortyquest.databinding.ActivityEmployeeFormBinding;
import com.example.rickandmortyquest.domain.model.Employee;
import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

public class EmployeeFormActivity extends BaseActivity<ActivityEmployeeFormBinding> {

    public static final String EXTRA_EMPLOYEE = "extra_employee";

    private EmployeeFormViewModel viewModel;
    private Employee currentEmployee;

    @Override
    protected ActivityEmployeeFormBinding inflateBinding() {
        return ActivityEmployeeFormBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        setupViewModel();

        currentEmployee = getEmployeeFromIntent();

        if (currentEmployee != null) {
            binding.titleTextView.setText("Editar funcionario");
            fillForm(currentEmployee);
        } else {
            binding.titleTextView.setText("Novo funcionario");
            binding.activeSwitch.setChecked(true);
        }
    }

    @Override
    protected void setupObservers() {
        viewModel.getSaveState().observe(this, this::renderSaveState);
    }

    @Override
    protected void setupListeners() {
        binding.saveButton.setOnClickListener(view -> saveEmployee());
    }

    private void setupViewModel() {
        HttpRequestExecutor executor = new HttpRequestExecutor();
        EmployeeRemoteDataSource remoteDataSource = new EmployeeRemoteDataSource(executor);
        EmployeeRepository repository = new EmployeeRepositoryImpl(remoteDataSource);
        EmployeeFormViewModelFactory factory = new EmployeeFormViewModelFactory(repository);

        viewModel = new ViewModelProvider(this, factory).get(EmployeeFormViewModel.class);
    }

    private void saveEmployee() {
        clearInputErrors();

        String nome = getText(binding.nameEditText.getText());
        String email = getText(binding.emailEditText.getText());
        String cargo = getText(binding.roleEditText.getText());
        String salario = getText(binding.salaryEditText.getText());
        boolean ativo = binding.activeSwitch.isChecked();

        viewModel.saveEmployee(currentEmployee, nome, email, cargo, salario, ativo);
    }

    private void fillForm(Employee employee) {
        binding.nameEditText.setText(employee.getNome());
        binding.emailEditText.setText(employee.getEmail());
        binding.roleEditText.setText(employee.getCargo());

        if (employee.getSalario() != null) {
            binding.salaryEditText.setText(employee.getSalario().toPlainString());
        }

        binding.activeSwitch.setChecked(employee.isActive());
    }

    private void renderSaveState(UiState<Employee> state) {
        if (state instanceof UiState.Idle) {
            setLoading(false);
            return;
        }

        if (state instanceof UiState.Loading) {
            setLoading(true);
            return;
        }

        if (state instanceof UiState.Success) {
            setLoading(false);
            showSnackbar("Funcionario salvo com sucesso.");
            setResult(Activity.RESULT_OK);
            finish();
            return;
        }

        if (state instanceof UiState.Error) {
            setLoading(false);

            UiState.Error<Employee> errorState = (UiState.Error<Employee>) state;
            String message = errorState.getMessage();

            showSnackbar(message);
            applyFieldErrorIfNeeded(message);
        }
    }

    private void setLoading(boolean isLoading) {
        ViewUtils.visibleIf(binding.loadingProgressBar, isLoading);
        ViewUtils.enabledIf(binding.saveButton, !isLoading);
        ViewUtils.enabledIf(binding.nameEditText, !isLoading);
        ViewUtils.enabledIf(binding.emailEditText, !isLoading);
        ViewUtils.enabledIf(binding.roleEditText, !isLoading);
        ViewUtils.enabledIf(binding.salaryEditText, !isLoading);
        ViewUtils.enabledIf(binding.activeSwitch, !isLoading);
    }

    private void clearInputErrors() {
        binding.nameInputLayout.setError(null);
        binding.emailInputLayout.setError(null);
        binding.roleInputLayout.setError(null);
        binding.salaryInputLayout.setError(null);
    }

    private void applyFieldErrorIfNeeded(String message) {
        if ("Informe o nome.".equals(message)) {
            binding.nameInputLayout.setError(message);
            return;
        }

        if ("Informe um e-mail valido.".equals(message)
                || "Informe um e-mail válido.".equals(message)) {
            binding.emailInputLayout.setError(message);
            return;
        }

        if ("Informe o cargo.".equals(message)) {
            binding.roleInputLayout.setError(message);
            return;
        }

        if ("Informe um salario valido.".equals(message)
                || "Informe um salário válido.".equals(message)
                || "O salario nao pode ser negativo.".equals(message)
                || "O salário não pode ser negativo.".equals(message)) {
            binding.salaryInputLayout.setError(message);
        }
    }

    private Employee getEmployeeFromIntent() {
        Intent intent = getIntent();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            return intent.getSerializableExtra(EXTRA_EMPLOYEE, Employee.class);
        }

        Object serializable = intent.getSerializableExtra(EXTRA_EMPLOYEE);

        if (serializable instanceof Employee) {
            return (Employee) serializable;
        }

        return null;
    }

    private String getText(CharSequence value) {
        return value == null ? "" : value.toString();
    }
}