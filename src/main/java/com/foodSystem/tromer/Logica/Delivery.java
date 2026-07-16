/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DELIVERY")
public class Delivery extends Destino {
    private String direccion;

    public Delivery() {
    }
    
    @Override
    void Enviar(String direccion) {
        System.out.println("enviar a direccion: "+direccion);
    }
    
    
    
}
