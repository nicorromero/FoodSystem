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
<<<<<<< HEAD
    BEBIDA; 
=======
    BEBIDA ;
>>>>>>> 4c2bd3d9acd74446016ef9a2575f6e7f085fb42b
    
    public static Categoria desdeString(String texto){
        if(texto == null){
            return null;
        }
        try{
            return Categoria.valueOf(texto.trim().toUpperCase());
<<<<<<< HEAD
        }catch(IllegalArgumentException e){
=======
        }
        catch(IllegalArgumentException e){
>>>>>>> 4c2bd3d9acd74446016ef9a2575f6e7f085fb42b
            return null;
        }
    }
}
