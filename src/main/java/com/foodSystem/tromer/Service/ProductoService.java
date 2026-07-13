/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Categoria;
import com.foodSystem.tromer.Producto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas
 */
@Service
public class ProductoService {
    
    private List<Producto> listaProductos = new ArrayList<>() ;
   
   public Producto registrarProducto(String nombre , double precio, String categoria){
    if(precio <= 0){
        throw new IllegalArgumentException("el precio no puede ser menor a 0");
    }
    if(nombre == null || nombre.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no puede estar vacio");
    } 
    Categoria cat = Categoria.desdeString(categoria);
    Producto p1 = new Producto(nombre,  cat,  precio);
    listaProductos.add(p1);
    return p1;
   }
   
   public boolean eliminarProducto(String nombre){
       if(nombre == null || nombre.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no puede estar vacio");
    }
       return listaProductos.removeIf(prod -> prod.getNombre().equalsIgnoreCase(nombre));
   }
   
  public boolean editarProducto(String nombreActual, String nuevoNombre, double nuevoPrecio, String nuevaCategoria) {
    
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
    }
    if (nuevoPrecio <= 0) {
        throw new IllegalArgumentException("El nuevo precio no puede ser menor o igual a 0");
    }
    
    for (Producto prod : listaProductos) {
        if (prod.getNombre().equalsIgnoreCase(nombreActual)) {
            
            
            prod.setNombre(nuevoNombre); 
            prod.setPrecio(nuevoPrecio);
            
            Categoria catNueva = Categoria.desdeString(nuevaCategoria); 
            if (catNueva != null) {
                prod.setCategoria(catNueva);
            }
            
            return true; 
        }
    }
    
    return false; 
}
  
  
  public void mostrarProductos(){
      if(listaProductos.isEmpty()){
          throw new IllegalArgumentException("no hay ningun producto cargado");
      }
      
    System.out.println("lista de productos");
    for(Producto prod : listaProductos){
        System.out.println("producto: "+prod.getNombre()+
                          "categoria: "+prod.getCategoria()+
                          "precio: "+prod.getPrecio());
    }
    
    
      
  }
    
}
