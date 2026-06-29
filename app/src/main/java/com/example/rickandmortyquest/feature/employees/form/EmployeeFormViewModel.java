package com.example.rickandmortyquest.feature.employees.form;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmortyquest.core.base.BaseViewModel;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.domain.model.Employee;
import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

import java.math.BigDecimal;

public class EmployeeFormViewModel extends BaseViewModel {

    private final EmployeeRepository repository;

    private final MutableLiveData<UiState<Employee>> saveState =
            new MutableLiveData<>(UiState.idle());

    public EmployeeFormViewModel(EmployeeRepository repository) {
        this.repository = repository;
    }

    public LiveData<UiState<Employee>> getSaveState() {
        return saveState;
    }

    public void saveEmployee(Employee currentEmployee, String nome, String email, String cargo, String salarioText, boolean ativo) {
        String normalizedNome = normalize(nome);
        String normalizedEmail = normalize(email);
        String normalizedCargo = normalize(cargo);
        String normalizedSalario = normalize(salarioText).replace(",", ".");

        if (normalizedNome.isEmpty()) {
            saveState.setValue(UiState.error("Informe o nome."));
            return;
        }

        if (normalizedEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(normalizedEmail).matches()) {
            saveState.setValue(UiState.error("Informe um e-mail válido."));
            return;
        }

        if (normalizedCargo.isEmpty()) {
            saveState.setValue(UiState.error("Informe o cargo."));
            return;
        }

        BigDecimal salario;
        try {
            salario = new BigDecimal(normalizedSalario);
        } catch (Exception exception) {
            saveState.setValue(UiState.error("Informe um salário válido."));
            return;
        }

        if (salario.compareTo(BigDecimal.ZERO) < 0) {
            saveState.setValue(UiState.error("O salário não pode ser negativo."));
            return;
        }

        Employee employee = currentEmployee != null ? currentEmployee : new Employee();
        employee.setNome(normalizedNome);
        employee.setEmail(normalizedEmail);
        employee.setCargo(normalizedCargo);
        employee.setSalario(salario);
        employee.setAtivo(ativo);

        saveState.setValue(UiState.loading());

        if (employee.getId() == null) {
            repository.createEmployee(employee, createCallback());
        } else {
            repository.updateEmployee(employee, createCallback());
        }
    }

    private ApiCallback<Employee> createCallback() {
        return new ApiCallback<Employee>() {
            @Override
            public void onSuccess(Employee data) {
                saveState.setValue(UiState.success(data));
            }

            @Override
            public void onError(Throwable throwable) {
                saveState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
            }
        };
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}