/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.foodSystem.tromer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicor
 */
public class Pedido {
    private String cliente;
    private List<Producto> item;
    private boolean estado;
    private Destino destino;
    private LocalDateTime fecha;
    private double total;
    

    public String getCliente() {
        return cliente;
    }

    public List<Producto> getItem() {
        return item;
    }

    public boolean isEstado() {
        return estado;
    }

    public Destino getDestino() {
        return destino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public Pedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total) {
        this.cliente = cliente;
        this.estado = estado;
        this.destino = destino;
        this.fecha = fecha;
        this.total = total;
        this.item = new ArrayList();
    }

    
}
