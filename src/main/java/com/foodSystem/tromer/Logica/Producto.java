package com.foodSystem.tromer.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

/**
 * Entidad que representa un producto del menú.
 * Nota sobre equals/hashCode: implementados sobre 'id' con hashCode constante,
 * siguiendo el patrón recomendado para entidades JPA usadas como keys en colecciones
 * (p.ej. Map<Producto, Integer> en Pedido). Esto garantiza consistencia antes y
 * después de la persistencia.
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "La categoría del producto es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Categoria categoria;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(nullable = false)
    private Double precio;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Producto() {}

    /**
     * Constructor de fábrica para crear un Producto en estado válido.
     *
     * @param nombre    Nombre del producto.
     * @param categoria Categoría del producto.
     * @param precio    Precio unitario (debe ser positivo).
     */
    public Producto(String nombre, Categoria categoria, Double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    // --- Getters ---
    // setId() eliminado: la PK es responsabilidad exclusiva de la BD y de Hibernate.

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    // --- equals / hashCode ---
    // Crítico: Producto se usa como KEY en Map<Producto, Integer>.
    // Sin esta implementación, dos instancias del mismo producto cargadas desde BD
    // serían consideradas distintas, causando duplicados en pedido_items.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        // Comparar por ID solo cuando ambos están persistidos
        return id != null && Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        // Constante fija: patrón recomendado por Vlad Mihalcea para entidades JPA.
        // Garantiza que el hashCode no cambie antes y después de la persistencia.
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "', categoria=" + categoria + ", precio=" + precio + "}";
    }
}
