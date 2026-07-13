/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foodSystem.tromer.Logica;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author nicor
 */
public class Gestor {
    
    private GestorProducto gestorProducto;
    private GestorPedido gestorPedido;
    private GestorReserva gestorReserva;

    public Gestor(GestorProducto gestorProducto, GestorPedido gestorPedido, GestorReserva gestorReserva) {
        this.gestorProducto = gestorProducto;
        this.gestorPedido = gestorPedido;
        this.gestorReserva = gestorReserva;
    }
   

    //FUNCIONES DE PRODUCTOS
    public Producto registrarNuevoProducto(String nombre , double precio, String categoria){
        return gestorProducto.registrarProducto(nombre, precio, categoria);
    }
    
    public boolean eliminarProducto(String nombre){
        return gestorProducto.eliminarProducto(nombre);
    }
    
    public boolean editarProductoExistente(String nombreActual, String nuevoNombre, double nuevoPrecio, String nuevaCategoria){
        return gestorProducto.editarProducto(nombreActual, nuevoNombre, nuevoPrecio, nuevaCategoria);
    }
    
    public void mostrarProductos(){
        gestorProducto.mostrarProductos();
    }
    
    //FUNCIONES DE PEDIDOS
    
    public Pedido registrarNuevoPedido(String cliente, boolean estado, Destino destino, LocalDateTime fecha, double total){
        return gestorPedido.registrarPedido(cliente, estado, destino, fecha, total);
    }
    
    public boolean eliminarPedido(String cliente){
        return gestorPedido.eliminarPedido(cliente);
    }
    
    public boolean editarPedidoExistente(String clienteActual, String nuevoNombre, boolean nuevoEstado,Destino nuevoDestino){
        return gestorPedido.editarPedido(clienteActual, nuevoNombre, nuevoEstado, nuevoDestino);
    }
    
    public void mostrarPedidos(){
        gestorPedido.mostrarPedido();
    }
    
    //FUNCIONES DE RESERVAS
    
    public Reserva registrarNuevaReserva(String cliente, int cantidad, LocalDateTime fecha){
        return gestorReserva.registrarReserva(cliente, cantidad, fecha);
    }
    
    public boolean eliminarReserva(String cliente){
        return gestorReserva.eliminarReserva(cliente);
    }
    
    public boolean editarReservaExistente(String clienteActual, String nuevoNombre, int nuevaCantidad){
        return gestorReserva.editarReserva(clienteActual, nuevoNombre, nuevaCantidad);
    }
    
    public void mostrarReservas(){
        gestorReserva.mostrarReservas();
    }
    


}
