/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MESA")
public class Mesa extends Destino {
 
    private String numMesa;

    public Mesa() {
    }
    
    @Override
    void Enviar(String numMesa) {
        System.out.println("su mesa es:"+numMesa);
    }
    
}
