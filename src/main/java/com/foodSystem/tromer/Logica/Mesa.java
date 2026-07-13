/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

/**
 *
 * @author nicor
 */
public class Mesa extends Destino {
 
    private String numMesa;
    
    @Override
    void Enviar(String numMesa) {
        System.out.println("su mesa es:"+numMesa);
    }
    
}
