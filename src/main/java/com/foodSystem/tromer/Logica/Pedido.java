package com.foodSystem.tromer.Logica;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Entidad central del sistema. Representa un pedido realizado por un cliente.
 *
 * Diseño de dominio:
 * - El estado es un enum {@link EstadoPedido} con ciclo de vida controlado.
 * - El total se calcula automáticamente a partir de los ítems (no se inyecta externamente).
 * - La colección 'items' se muta de forma segura para preservar el proxy de Hibernate.
 * - El constructor principal garantiza un estado inicial siempre válido.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Column(nullable = false, length = 150)
    private String cliente;

    /**
     * Mapa de productos y sus cantidades para este pedido.
     * IMPORTANTE: se usa {@link ElementCollection} con un proxy de Hibernate.
     * Nunca reemplazar la referencia directamente (this.items = nuevaReferencia).
     * Usar siempre los métodos de dominio: agregarItem(), quitarItem(), setItems().
     */
    @ElementCollection
    @CollectionTable(name = "pedido_items", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad", nullable = false)
    private Map<Producto, Integer> items = new HashMap<>();

    @NotNull(message = "El estado del pedido es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @NotNull(message = "El destino del pedido es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fecha;

    @PositiveOrZero(message = "El total no puede ser negativo")
    @Column(nullable = false)
    private double total;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Pedido() {}

    /**
     * Constructor de fábrica. Garantiza que el Pedido nace en un estado siempre válido:
     * estado=PENDIENTE, total=0, fecha=ahora.
     *
     * @param cliente Nombre del cliente que realiza el pedido.
     * @param destino Destino del pedido (Mesa o Delivery). No puede ser nulo.
     */
    public Pedido(String cliente, Destino destino) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        Objects.requireNonNull(destino, "El destino no puede ser nulo");
        this.cliente = cliente;
        this.destino = destino;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
        this.total = 0.0;
    }

    // ===== MÉTODOS DE DOMINIO =====

    /**
     * Agrega un producto al pedido o incrementa su cantidad si ya existe.
     * Recalcula el total automáticamente.
     *
     * @param producto El producto a agregar. No puede ser nulo.
     * @param cantidad La cantidad a agregar. Debe ser positiva.
     */
    public void agregarItem(Producto producto, int cantidad) {
        Objects.requireNonNull(producto, "El producto no puede ser nulo");
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser un valor positivo, se recibió: " + cantidad);
        }
        this.items.merge(producto, cantidad, Integer::sum);
        recalcularTotal();
    }

    /**
     * Elimina un producto del pedido por completo.
     * Recalcula el total automáticamente.
     *
     * @param producto El producto a quitar.
     */
    public void quitarItem(Producto producto) {
        this.items.remove(producto);
        recalcularTotal();
    }

    /**
     * Recalcula el total sumando (precio * cantidad) de cada ítem.
     * Se invoca internamente cada vez que los ítems cambian.
     */
    public void recalcularTotal() {
        this.total = items.entrySet().stream()
            .mapToDouble(e -> e.getKey().getPrecio() * e.getValue())
            .sum();
    }

    /**
     * Avanza el estado del pedido al siguiente en el ciclo de vida.
     * PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO
     *
     * @throws IllegalStateException si el pedido ya está en estado terminal (ENTREGADO o CANCELADO).
     */
    public void avanzarEstado() {
        if (this.estado == EstadoPedido.ENTREGADO || this.estado == EstadoPedido.CANCELADO) {
            throw new IllegalStateException(
                "No se puede avanzar el estado de un pedido en estado: " + this.estado
            );
        }
        this.estado = EstadoPedido.values()[this.estado.ordinal() + 1];
    }

    /**
     * Cancela el pedido. Es un estado terminal.
     *
     * @throws IllegalStateException si el pedido ya fue entregado.
     */
    public void cancelar() {
        if (this.estado == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No se puede cancelar un pedido ya entregado.");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    // ===== GETTERS Y SETTERS =====
    // setId() eliminado: la PK es responsabilidad exclusiva de la BD.
    // setTotal() eliminado: el total es una invariante derivada de los ítems.
    // setFecha() eliminado: la fecha de creación no debe cambiar.

    public Long getId() { return id; }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    /**
     * Retorna una vista no modificable de los ítems del pedido.
     * Para agregar/quitar ítems usar agregarItem() y quitarItem().
     */
    public Map<Producto, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Reemplaza todos los ítems del pedido.
     * IMPORTANTE: muta la colección existente (clear + putAll) para preservar
     * el proxy de Hibernate y evitar datos huérfanos en la tabla pedido_items.
     *
     * @param items Nuevos ítems del pedido. Puede ser null para limpiar.
     */
    public void setItems(Map<Producto, Integer> items) {
        this.items.clear();
        if (items != null) {
            this.items.putAll(items);
        }
        recalcularTotal();
    }

    public EstadoPedido getEstado() { return estado; }

    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public Destino getDestino() { return destino; }

    public void setDestino(Destino destino) { this.destino = destino; }

    public LocalDateTime getFecha() { return fecha; }

    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente='" + cliente + "', estado=" + estado +
               ", total=" + total + ", fecha=" + fecha + "}";
    }
}