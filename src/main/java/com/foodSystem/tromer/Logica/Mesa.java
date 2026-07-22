package com.foodSystem.tromer.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

/**
 * Destino concreto para pedidos atendidos en una mesa del local.
 */
@Entity
@DiscriminatorValue("MESA")
public class Mesa extends Destino {

    @NotBlank(message = "El número de mesa es obligatorio")
    @Column(name = "num_mesa", nullable = false, length = 10)
    private String numMesa;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Mesa() {}

    /**
     * Constructor de fábrica para crear una Mesa en estado válido.
     *
     * @param nombre  Nombre o alias del destino (ej. "Salón Principal").
     * @param numMesa Identificador visual de la mesa (ej. "12", "A-3").
     */
    public Mesa(String nombre, String numMesa) {
        super(nombre);
        this.numMesa = numMesa;
    }

    public String getNumMesa() { return numMesa; }

    public void setNumMesa(String numMesa) { this.numMesa = numMesa; }

    @Override
    public void enviar(String destino) {
        System.out.println("Pedido asignado a la mesa: " + this.numMesa);
    }
}
