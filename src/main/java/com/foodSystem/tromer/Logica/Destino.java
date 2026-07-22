package com.foodSystem.tromer.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad abstracta que representa el destino de un Pedido.
 * Sus subclases concretas son Mesa (consumo en local) y Delivery (envío a domicilio).
 *
 * Estrategia SINGLE_TABLE: Mesa y Delivery se almacenan en la misma tabla 'destinos',
 * distinguidas por la columna discriminadora 'tipo_destino'.
 */
@Entity
@Table(name = "destinos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "tipo_destino",
    discriminatorType = DiscriminatorType.STRING,
    columnDefinition = "VARCHAR(20)"
)
public abstract class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long objeto (no primitivo) para distinguir 'sin ID' (null) de 'ID = 0'

    @NotBlank(message = "El nombre del destino es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Destino() {}

    /**
     * Constructor base para subclases.
     *
     * @param nombre Nombre descriptivo del destino.
     */
    protected Destino(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    /** Setter de visibilidad protegida: solo las subclases pueden modificar el nombre. */
    protected void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Lógica de negocio: procesa el envío al destino concreto.
     *
     * @param destino Información adicional de envío (dirección, número de mesa, etc.).
     */
    public abstract void enviar(String destino);
}
