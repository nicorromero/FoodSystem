/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Service;


import com.foodSystem.tromer.Logica.Destino;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Logica.Reserva;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas

 * @author nicor
*/

@Service
public class GestorService {
    
    private ProductoService gestorProducto;
    private PedidoService gestorPedido;
    private ReservaService gestorReserva;

    public GestorService(ProductoService gestorProducto, PedidoService gestorPedido, ReservaService gestorReserva) {
        this.gestorProducto = gestorProducto;
        this.gestorPedido = gestorPedido;
        this.gestorReserva = gestorReserva;
    }
    
    public GestorService (){};
    
    //FUNCIONES DE PRODUCTOS
    public Producto registrarNuevoProducto(String nombre , double precio, String categoria){
        return gestorProducto.registrarProducto(nombre, precio, categoria);
    }
    
    public boolean eliminarProducto(Long id){
        return gestorProducto.eliminarProducto(id);
    }
    
    public boolean editarProductoExistente(Long id, String nuevoNombre, double nuevoPrecio, String nuevaCategoria){
        return gestorProducto.editarProducto(id, nuevoNombre, 0, nuevaCategoria);
    }
    
    public void mostrarProductos(){
        gestorProducto.mostrarProductos();
    }
    
    //FUNCIONES DE PEDIDOS
    
    public Pedido registrarNuevoPedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total){
        return gestorPedido.registrarPedido(cliente, estado, destino, fecha, total);
    }
    
    public boolean eliminarPedido(Long id){
        return gestorPedido.eliminarPedido(id);
    }
    
    public boolean editarPedidoExistente(Long id, String nuevoNombre, boolean nuevoEstado,Destino nuevoDestino){
        return gestorPedido.editarPedido(id, nuevoNombre, nuevoEstado, nuevoDestino);
    }
    
    public void mostrarPedidos(){
        gestorPedido.mostrarPedido();
    }
    
    //FUNCIONES DE RESERVAS
    
    public Reserva registrarNuevaReserva(String cliente, int cantidad, LocalDateTime fecha){
        return gestorReserva.registrarReserva(cliente, cantidad, fecha);
    }
    
    public boolean eliminarReserva(Long id){
        return gestorReserva.eliminarReserva(id);
    }
    
    public boolean editarReservaExistente(Long id, String nuevoNombre, int nuevaCantidad){
        return gestorReserva.eliminarReserva(id);
    }
    
    public void mostrarReservas(){
        gestorReserva.mostrarReservas();
    }
    

}
