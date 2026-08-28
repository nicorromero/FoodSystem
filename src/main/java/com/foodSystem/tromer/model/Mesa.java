package com.foodSystem.tromer.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;

/**
 * Destino concreto para pedidos atendidos en una mesa del local.
 */
@Entity
@DiscriminatorValue("MESA")
public class Mesa extends Destino {
    @Min(value = 1, message = "La mesa debe tener al menos 1 lugar")
    @Column(name = "cant_lugares", nullable = false)
    private int cantLugares;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Mesa() {
    }

    /**
     * Constructor de fábrica para crear una Mesa en estado válido.
     *
     * @param nombre   Nombre o alias del destino (ej. "Salón Principal").
     * @param cantidad
     */

    public Mesa(String nombre, int cantidad) {
        super(nombre);
        this.cantLugares = cantidad;
    }

    public int getNumMesa() {
        return cantLugares;
    }

    public void setNumMesa(int numMesa) {
        this.cantLugares = numMesa;
    }

    @Override
    public void enviar(String destino) {
        System.out.println("Pedido asignado a la mesa: " + this.cantLugares);
    }
}
