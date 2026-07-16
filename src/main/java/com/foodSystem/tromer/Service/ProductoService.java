/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Repository.ProductoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas
 */
@Service
public class ProductoService {
    
   private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
   
   
   
   public Producto registrarProducto(String nombre , double precio, String categoria){
    if(precio <= 0){
        throw new IllegalArgumentException("el precio no puede ser menor a 0");
    }
    if(nombre == null || nombre.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no puede estar vacio");
    } 
    Categoria cat = Categoria.desdeString(categoria);
    Producto p1 = new Producto(nombre,  cat,  precio);
    
    return productoRepository.save(p1);
   }
   
   public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
   
   public boolean editarProducto(Long id, String nuevoNombre, double nuevoPrecio, String nuevaCategoria) {
    //Buscamos el producto en la base de datos por su ID
    return productoRepository.findById(id).map(productoExistente -> {
        
        //Validamos los nuevos datos antes de aplicarlos
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
        }
        if (nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El nuevo precio no puede ser menor o igual a 0");
        }

        //Aplicamos los cambios al objeto recuperado
        productoExistente.setNombre(nuevoNombre);
        productoExistente.setPrecio(nuevoPrecio);
        
        Categoria catNueva = Categoria.desdeString(nuevaCategoria);
        if (catNueva != null) {
            productoExistente.setCategoria(catNueva);
        }

        // Guardamos los cambios. Al tener el mismo ID, JPA hace un UPDATE automáticamente
        productoRepository.save(productoExistente);
        return true;
        
    }).orElse(false); // Retorna false si no se encontró el producto con ese ID
   }
  
  
    public List<Producto> mostrarProductos() {
      
    List<Producto> lista = productoRepository.findAll();
    
    if (lista.isEmpty()) {
        throw new IllegalArgumentException("No se encuentran pedidos en la base de datos");
    }
    return lista;
    }
   }
    
    
      
  
    

