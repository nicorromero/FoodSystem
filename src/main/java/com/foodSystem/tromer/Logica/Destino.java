/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity

// @Inheritance define cómo se van a guardar los "hijos" de esta clase. 
// 'SINGLE_TABLE' significa que va a meter a Mesa y Delivery en una sola tabla grande llamada 'destino'.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)


// que le va a servir a MySQL para distinguir si la fila es un Delivery o una Mesa.
@DiscriminatorColumn(name = "tipo_destino")
 public abstract class Destino {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     private String nombre;

    public Destino() {
    }
     
abstract void Enviar ( String nombre);
    
    
  
}
