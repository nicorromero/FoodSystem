/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.foodSystem.tromer;

/**
 *
 * @author nicor
 */
public class Producto {
    private String nombre;
    private Categoria categoria;
    private Double precio;

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public Producto(String nombre, Categoria categoria, Double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }
    
    
    
}
