package com.foodSystem.tromer.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entidad que representa una reserva de mesa realizada por un cliente.
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long objeto (no long primitivo) para representar 'sin ID' como null

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Column(nullable = false, length = 150)
    private String cliente;

    @Min(value = 1, message = "La reserva debe ser para al menos 1 persona")
    @Max(value = 50, message = "La reserva no puede ser para más de 50 personas")
    @Column(nullable = false)
    private int cantidad;

    @NotNull(message = "La fecha de la reserva es obligatoria")
    @FutureOrPresent(message = "La fecha de la reserva no puede ser en el pasado")
    @Column(nullable = false)
    private LocalDateTime fecha;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Reserva() {}

    /**
     * Constructor de fábrica para crear una Reserva en estado válido.
     *
     * @param cliente  Nombre del cliente que realiza la reserva.
     * @param cantidad Número de personas de la reserva.
     * @param fecha    Fecha y hora de la reserva (no puede ser en el pasado).
     */
    public Reserva(String cliente, int cantidad, LocalDateTime fecha) {
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    // --- Getters y Setters ---
    // getId() sin setId(): la PK es responsabilidad de la BD y Hibernate.

    public Long getId() { return id; }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFecha() { return fecha; }

    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Reserva{id=" + id + ", cliente='" + cliente + "', cantidad=" + cantidad + ", fecha=" + fecha + "}";
    }
}
