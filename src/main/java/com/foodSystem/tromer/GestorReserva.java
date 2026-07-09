/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas
 */
public class GestorReserva {
    
    private List<Reserva> listaReservas = new ArrayList<>();
    
    public Reserva registrarReserva (String cliente, int cantidad, LocalDateTime fecha){
        if(cantidad <= 0){
            throw new IllegalArgumentException("la cantidad de personas debe ser mayor a 0");
        }
        if(cliente == null || cliente.trim().isEmpty()){
            throw new IllegalArgumentException("ingrese el cliente");
       }
       Reserva r1 = new Reserva(cliente,cantidad,fecha);
       listaReservas.add(r1);
       return r1;
    }
    
    public Boolean eliminarReserva(String cliente){
       if(cliente == null || cliente.trim().isEmpty()){
        throw new IllegalArgumentException("el nombre no puede estar vacio");
    }
       return listaReservas.removeIf(res -> res.getCliente().equalsIgnoreCase(cliente));
   }
    
    public boolean editarReserva (String clienteActual, String nuevoNombre, int nuevaCantidad) {
    
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
    }
    
    if(nuevaCantidad <= 0){
        throw new IllegalArgumentException("la cantidad debe ser mayor a 0");
    }
    
    for (Reserva res : listaReservas) {
        if (res.getCliente().equalsIgnoreCase(clienteActual)) {
             
            res.setCliente(nuevoNombre);
            
            res.setCantidad(nuevaCantidad);
            
            return true; 
        }
    }
    
    return false; 
}
    
    public void mostrarReservas(){
        if(listaReservas.isEmpty()){
            throw new IllegalArgumentException("no hay reservas");
        }
        System.out.println("lista de reservas: ");
        
        for(Reserva res : listaReservas){
            System.out.println("cliente: "+res.getCliente()+
                              "cantdad de personas: "+res.getCantidad()+
                              "fecha: "+res.getFecha());
        }
    }
    
}
