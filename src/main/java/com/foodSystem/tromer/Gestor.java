/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author nicor
 */
public class Gestor {
    private List<Pedido> listaPedidos;
    private List<Producto> ListaProductos;
    private List<Reserva> ListaReservas;

   public Producto registrarProducto(String nombre , double precio, String categoria){
    if(precio <= 0){
        throw new IllegalArgumentException("el precio no puede ser menor a 0");
    }
    if(nombre == null || nombre.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no existe");
    } 
    Categoria cat = Categoria.desdeString(categoria);
    Producto p1 = new Producto(nombre,  cat,  precio);
    ListaProductos.add(p1);
    return p1;
   }
   
   /*
   public Pedido registrarPedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total){
       if(total<= 0){
          throw new IllegalArgumentException("el total no puede ser menor a 0");
       }
       if(cliente == null) || cliente.trim().isEmpty(){
            throw new IllegalArgumentException("ingrese el cliente");
       }   
   }
    */
}
