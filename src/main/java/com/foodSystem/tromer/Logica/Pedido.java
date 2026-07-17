/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import jakarta.persistence.*;

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
>>>>>>> 8f831f9fccf6e0f7071fcce165d13520ae752a6a
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

    @Entity
    @Table(name = "pedidos") // Le damos el nombre plural a la tabla en la BD
    public class Pedido {

    @Id // Obligatorio: Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

<<<<<<< HEAD
    private String cliente;

    // Mapeo del HashMap para guardar productos y sus cantidades
    @ElementCollection
    @CollectionTable(name = "pedido_items", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyJoinColumn(name = "producto_id") // Asume que tienes una entidad Producto
    @Column(name = "cantidad")
    private Map<Producto, Integer> items = new HashMap<>();

    private boolean estado;

    // Asumimos que Destino es otra Entidad. Si es un Enum, usa @Enumerated(EnumType.STRING)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id")
=======
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
    
>>>>>>> 8f831f9fccf6e0f7071fcce165d13520ae752a6a
    private Destino destino;

    private LocalDateTime fecha;

    private double total;

<<<<<<< HEAD
    // 1. CONSTRUCTOR VACÍO (Obligatorio para que la Base de Datos funcione)
    public Pedido() {
=======
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
>>>>>>> 8f831f9fccf6e0f7071fcce165d13520ae752a6a
    }

    // 2. CONSTRUCTOR CON PARÁMETROS (Corregido, sin el ArrayList)
    public Pedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total) {
        this.cliente = cliente;
        this.estado = estado;
        this.destino = destino;
        this.fecha = fecha;
        this.total = total;
    }

    // --- GETTERS Y SETTERS COMPLETO ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Map<Producto, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Producto, Integer> items) {
        this.items = items;
    }

    public boolean getEstado() {
        return estado; // Por convención, para los booleanos también se puede usar isEstado()
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}