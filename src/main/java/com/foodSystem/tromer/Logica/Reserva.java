/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import java.time.LocalDateTime;

/**
 *
 * @author nicor
 */
public class Reserva {
    private String cliente;
    private int cantidad;
    private LocalDateTime fecha;

    public String getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Reserva(String cliente, int cantidad, LocalDateTime fecha) {
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
