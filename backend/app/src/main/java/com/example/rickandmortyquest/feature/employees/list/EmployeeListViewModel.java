package com.example.rickandmortyquest.feature.employees.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmortyquest.core.base.BaseViewModel;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.state.UiState;
import com.example.rickandmortyquest.core.utils.Event;
import com.example.rickandmortyquest.domain.model.Employee;
import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

import java.util.List;

public class EmployeeListViewModel extends BaseViewModel {

    private final EmployeeRepository repository;

    private final MutableLiveData<UiState<List<Employee>>> employeeState =
            new MutableLiveData<>(UiState.idle());

    private final MutableLiveData<Event<String>> messageEvent = new MutableLiveData<>();

    public EmployeeListViewModel(EmployeeRepository repository) {
        this.repository = repository;
    }

    public LiveData<UiState<List<Employee>>> getEmployeeState() {
        return employeeState;
    }

    public LiveData<Event<String>> getMessageEvent() {
        return messageEvent;
    }

    public void loadEmployees() {
        employeeState.setValue(UiState.loading());

        repository.getEmployees(new ApiCallback<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> data) {
                if (data == null || data.isEmpty()) {
                    employeeState.setValue(UiState.empty("Nenhum funcionário encontrado."));
                    return;
                }

                employeeState.setValue(UiState.success(data));
            }

            @Override
            public void onError(Throwable throwable) {
                employeeState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
            }
        });
    }

    public void deleteEmployee(Employee employee) {
        if (employee == null || employee.getId() == null) {
            messageEvent.setValue(new Event<>("Funcionário inválido."));
            return;
        }

        employeeState.setValue(UiState.loading());

        repository.deleteEmployee(employee.getId(), new ApiCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                messageEvent.setValue(new Event<>("Funcionário excluído com sucesso."));
                loadEmployees();
            }

            @Override
            public void onError(Throwable throwable) {
                employeeState.setValue(UiState.error(getDefaultErrorMessage(throwable), throwable));
            }
        });
    }
}