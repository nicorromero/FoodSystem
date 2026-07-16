/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Logica.Destino;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Repository.PedidoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas
 */
@Service
public class PedidoService {
   
    private final PedidoRepository pedidoRepository;  
    
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    
    public Pedido registrarPedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total){
       if(total<= 0){
          throw new IllegalArgumentException("el total no puede ser menor a 0");
       }
       if(cliente == null || cliente.trim().isEmpty()){
            throw new IllegalArgumentException("ingrese el cliente");
       }
       Pedido nuevoPedido = new Pedido(cliente, estado, destino, fecha, total);
       
       return pedidoRepository.save(nuevoPedido);
    }
    
   public Boolean eliminarPedido(Long id){
       if (!pedidoRepository.existsById(id)) {
        
        throw new IllegalArgumentException("No se encontró la reserva con ID: " + id);
        }
         pedidoRepository.deleteById(id);
         return true;
    }
     
    public boolean editarPedido(Long id, String nuevoNombre, boolean nuevoEstado, Destino nuevoDestino) {
    return pedidoRepository.findById(id).map(ped -> {
        
        // Validaciones
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
        }
        if (nuevoDestino == null) {
            throw new IllegalArgumentException("Debes asignar un destino válido");
        }

        // Seteamos cambios
        ped.setCliente(nuevoNombre);
        ped.setEstado(nuevoEstado);
        ped.setDestino(nuevoDestino);

        pedidoRepository.save(ped); // JPA detecta el ID y hace un UPDATE
        return true;
        
    }).orElse(false);
}
    
    public List<Pedido> mostrarPedido(){
    
    List<Pedido> lista = pedidoRepository.findAll();
    
    if (lista.isEmpty()) {
        throw new IllegalArgumentException("No se encuentran pedidos en la base de datos");
    }
    return lista;
    }
    
}
