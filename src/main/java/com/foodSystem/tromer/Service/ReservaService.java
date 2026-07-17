/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Logica.Reserva;
import com.foodSystem.tromer.Repository.ReservaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas
 */
@Service
public class ReservaService {
    
    private  final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    
    
    
    public Reserva registrarReserva (String cliente, int cantidad, LocalDateTime fecha){
            if(cantidad <= 0){
                throw new IllegalArgumentException("la cantidad de personas debe ser mayor a 0");
            }
            if(cliente == null || cliente.trim().isEmpty()){
                throw new IllegalArgumentException("ingrese el cliente");
            }
           Reserva r1 = new Reserva(cliente,cantidad,fecha);
           
           return reservaRepository.save(r1);
    }
    
    public Boolean eliminarReserva(Long id){
       if (!reservaRepository.existsById(id)) {
        // Acá lanzás la excepción solo si es vital que el proceso se detenga
        throw new IllegalArgumentException("No se encontró la reserva con ID: " + id);
        }
         reservaRepository.deleteById(id);
         return true;
    }

    public boolean editarReserva (Long id, String nuevoNombre, int nuevaCantidad) {
         return reservaRepository.findById(id).map(res -> {
        
        // Validaciones
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
        }
        if (nuevaCantidad == 0) {
            throw new IllegalArgumentException("debes ingresar una cantidad de personas");
        }

        // Seteamos cambios
        res.setCliente(nuevoNombre);
        res.setCantidad(nuevaCantidad);

        reservaRepository.save(res); // JPA detecta el ID y hace un UPDATE
        return true;
        
    }).orElse(false);
    }
    
    public List<Reserva> mostrarReservas(){
      List<Reserva> lista = reservaRepository.findAll();
    
    if (lista.isEmpty()) {
        throw new IllegalArgumentException("No se encuentran pedidos en la base de datos");
    }
    return lista;
    }
    
}
