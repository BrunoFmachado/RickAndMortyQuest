package com.example.rickandmortyquest.domain.repository;

import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.domain.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    void getEmployees(ApiCallback<List<Employee>> callback);

    void createEmployee(Employee employee, ApiCallback<Employee> callback);

    void updateEmployee(Employee employee, ApiCallback<Employee> callback);

    void deleteEmployee(long id, ApiCallback<Void> callback);
}