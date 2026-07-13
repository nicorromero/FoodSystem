/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

/**
 *
 * @author nicor
 */
public class Delivery extends Destino {
    private String direccion;

    @Override
    void Enviar(String direccion) {
        System.out.println("enviar a direccion: "+direccion);
    }
    
    
    
}
