/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Destino;
import com.foodSystem.tromer.Pedido;
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
    private List<Pedido> listaPedidos = new ArrayList<>();
    
    public Pedido registrarPedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total){
       if(total<= 0){
          throw new IllegalArgumentException("el total no puede ser menor a 0");
       }
       if(cliente == null || cliente.trim().isEmpty()){
            throw new IllegalArgumentException("ingrese el cliente");
       }
       Pedido pe1 = new Pedido(cliente, estado, destino, fecha, total);
       listaPedidos.add(pe1);
       return pe1;
    }
    
     public Boolean eliminarPedido(String cliente){
       if(cliente == null || cliente.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no puede estar vacio");
    }
       return listaPedidos.removeIf(ped -> ped.getCliente().equalsIgnoreCase(cliente));
   }
     
    public boolean editarPedido(String clienteActual, String nuevoNombre, boolean nuevoEstado,Destino nuevoDestino) {
    
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
    }
    
    if(nuevoDestino == null){
        throw new IllegalArgumentException("debes asignar un destino valido");
    }
    
    for (Pedido ped : listaPedidos) {
        if (ped.getCliente().equalsIgnoreCase(clienteActual)) {
            
            //  Actualizamos los datos 
            ped.setCliente(nuevoNombre);
            
            // El boolean se asigna directo, sin validaciones
            ped.setEstado(nuevoEstado);
            
            ped.setDestino(nuevoDestino);
            
            return true; // Se actualizó con éxito
        }
    }
    
    return false; 
}
    
    public void mostrarPedido(){
        if(listaPedidos.isEmpty()){
            throw new IllegalArgumentException("no se encuentran pedidos");
        }
        System.out.println("lista de pedidos");
        for(Pedido ped : listaPedidos){
            System.out.println("cliente: "+ped.getCliente()+
                               "estado: "+ped.getEstado()+
                               "destino: "+ped.getDestino()+
                               "fecha: "+ped.getFecha()+
                               "total: "+ped.getTotal());
        }
    }
    
}
