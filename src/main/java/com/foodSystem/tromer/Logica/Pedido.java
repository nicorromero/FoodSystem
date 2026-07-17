/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

    @Entity
    @Table(name = "pedidos") // Le damos el nombre plural a la tabla en la BD
    public class Pedido {

    @Id // Obligatorio: Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

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
    private Destino destino;

    private LocalDateTime fecha;

    private double total;

    // 1. CONSTRUCTOR VACÍO (Obligatorio para que la Base de Datos funcione)
    public Pedido() {
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