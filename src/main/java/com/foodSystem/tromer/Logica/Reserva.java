/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 *
 *
 */
@Entity
public class Reserva {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id ;
    
    private String cliente;
    private int cantidad;
    private LocalDateTime fecha;

    public Reserva() {
    }

    public Reserva(String cliente, int cantidad, LocalDateTime fecha) {
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    
    
    public String getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
