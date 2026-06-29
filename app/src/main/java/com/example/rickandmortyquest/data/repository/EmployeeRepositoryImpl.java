package com.example.rickandmortyquest.data.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.data.remote.api.EmployeeRemoteDataSource;
import com.example.rickandmortyquest.domain.model.Employee;
import com.example.rickandmortyquest.domain.repository.EmployeeRepository;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeRemoteDataSource remoteDataSource;

    public EmployeeRepositoryImpl(EmployeeRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getEmployees(ApiCallback<List<Employee>> callback) {
        remoteDataSource.getEmployees(callback);
    }

    @Override
    public void createEmployee(Employee employee, ApiCallback<Employee> callback) {
        remoteDataSource.createEmployee(employee, callback);
    }

    @Override
    public void updateEmployee(Employee employee, ApiCallback<Employee> callback) {
        remoteDataSource.updateEmployee(employee, callback);
    }

    @Override
    public void deleteEmployee(long id, ApiCallback<Void> callback) {
        remoteDataSource.deleteEmployee(id, callback);
    }
}