package com.example.rickandmortyquest.data.remote.api;

import com.example.rickandmortyquest.BuildConfig;
import com.example.rickandmortyquest.core.network.ApiCallback;
import com.example.rickandmortyquest.core.network.GsonProvider;
import com.example.rickandmortyquest.core.network.HttpRequestExecutor;
import com.example.rickandmortyquest.domain.model.Employee;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class EmployeeRemoteDataSource {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final HttpRequestExecutor requestExecutor;

    public EmployeeRemoteDataSource(HttpRequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void getEmployees(ApiCallback<List<Employee>> callback) {
        Type responseType = new TypeToken<List<Employee>>() {
        }.getType();

        Request request = new Request.Builder()
                .url(BuildConfig.LOCAL_BACKEND_BASE_URL + "api/funcionarios")
                .get()
                .build();

        requestExecutor.execute(request, responseType, callback);
    }

    public void createEmployee(Employee employee, ApiCallback<Employee> callback) {
        Request request = new Request.Builder()
                .url(BuildConfig.LOCAL_BACKEND_BASE_URL + "api/funcionarios")
                .post(createBody(employee))
                .build();

        requestExecutor.execute(request, Employee.class, callback);
    }

    public void updateEmployee(Employee employee, ApiCallback<Employee> callback) {
        Request request = new Request.Builder()
                .url(BuildConfig.LOCAL_BACKEND_BASE_URL + "api/funcionarios/" + employee.getId())
                .put(createBody(employee))
                .build();

        requestExecutor.execute(request, Employee.class, callback);
    }

    public void deleteEmployee(long id, ApiCallback<Void> callback) {
        Request request = new Request.Builder()
                .url(BuildConfig.LOCAL_BACKEND_BASE_URL + "api/funcionarios/" + id)
                .delete()
                .build();

        requestExecutor.execute(request, Void.class, callback);
    }

    private RequestBody createBody(Employee employee) {
        String jsonBody = GsonProvider.getGson().toJson(employee);
        return RequestBody.create(jsonBody, JSON);
    }
}