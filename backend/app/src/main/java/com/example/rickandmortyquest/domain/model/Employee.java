package com.example.rickandmortyquest.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Employee implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String cargo;
    private BigDecimal salario;
    private Boolean ativo;
    private String dateCreated;

    public Employee() {
    }

    public Employee(Long id, String nome, String email, String cargo, BigDecimal salario, Boolean ativo, String dateCreated) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.salario = salario;
        this.ativo = ativo;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return valueOrDash(nome);
    }

    public String getEmail() {
        return valueOrDash(email);
    }

    public String getCargo() {
        return valueOrDash(cargo);
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public String getDateCreated() {
        return valueOrDash(dateCreated);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(ativo);
    }

    private String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}