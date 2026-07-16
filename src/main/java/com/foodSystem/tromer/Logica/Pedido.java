/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.foodSystem.tromer.Logica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    
    
    private long id ; 
    private String cliente;
    
    // @ManyToMany: Indica que la relación es "Muchos a Muchos". 
    // Un pedido tiene muchos productos, y un producto puede estar en muchos pedidos distintos.
    @ManyToMany
    // @JoinTable soluciona el problema de las listas creando una "tabla intermedia" en MySQL.
    @JoinTable(
      name = "pedido_producto",
      joinColumns = @JoinColumn(name = "pedido_id"), // Crea una columna con el ID de este pedido.
      inverseJoinColumns = @JoinColumn(name = "producto_id")) // Crea una columna con el ID del producto guarda
            
    private List<Producto> item;
    private boolean estado;
    
    // @OneToOne: Relación "Uno a Uno". Un pedido tiene asignado exactamente UN destino.
    // 'cascade = CascadeType.ALL' es magia pura: si vos guardás un Pedido nuevo, 
 
    @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn crea una columna física en la tabla 'pedido' llamada 'destino_id'. 
    // Esa columna guarda el número de ID que enlaza con la tabla 'destino'.
    @JoinColumn(name = "destino_id", referencedColumnName = "id")
    
    private Destino destino;
    private LocalDateTime fecha;
    private double total;

    public Pedido() {
    }
    
    
    public String getCliente() {
        return cliente;
    }

    public List<Producto> getItem() {
        return item;
    }

    public boolean getEstado() {
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

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    
}
