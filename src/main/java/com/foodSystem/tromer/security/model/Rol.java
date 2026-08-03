package com.foodSystem.tromer.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName nombre;

    public Rol() {
    }

    public Rol(RoleName nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getNombre() {
        return nombre;
    }

    public void setNombre(RoleName nombre) {
        this.nombre = nombre;
    }

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN
    }
}
