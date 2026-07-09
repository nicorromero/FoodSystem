/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package com.foodSystem.tromer;

/**
 *
 * @author nicor
 */
public enum Categoria{
    POSTRE,
    PLATO,
    BEBIDA; 
    
    public static Categoria desdeString(String texto){
        if(texto == null){
            return null;
        }
        try{
            return Categoria.valueOf(texto.trim().toUpperCase());
        }catch(IllegalArgumentException e){
            return null;
        }
    }
}
