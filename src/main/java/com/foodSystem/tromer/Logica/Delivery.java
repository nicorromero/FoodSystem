package com.foodSystem.tromer.Logica;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

/**
 * Destino concreto para pedidos enviados a una dirección física (domicilio).
 */
@Entity
@DiscriminatorValue("DELIVERY")
public class Delivery extends Destino {

    @NotBlank(message = "La dirección de delivery es obligatoria")
    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    /** Constructor requerido por JPA. No usar directamente en código de negocio. */
    protected Delivery() {}

    /**
     * Constructor de fábrica para crear un Delivery en estado válido.
     *
     * @param nombre    Nombre o alias del destino (ej. "Delivery Norte").
     * @param direccion Dirección física de entrega.
     */
    public Delivery(String nombre, String direccion) {
        super(nombre);
        this.direccion = direccion;
    }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public void enviar(String destino) {
        System.out.println("Enviando pedido a la dirección: " + this.direccion);
    }
}
