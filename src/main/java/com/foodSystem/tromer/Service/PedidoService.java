package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.Logica.Destino;
import com.foodSystem.tromer.Logica.EstadoPedido;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Repository.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para la gestión de Pedidos.
 * La lógica de construcción y mutación de estado se delega a los métodos de
 * dominio de la entidad Pedido, manteniendo este servicio como orquestador.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Registra un nuevo pedido. El estado inicial siempre será PENDIENTE
     * y la fecha se asigna automáticamente en el constructor de Pedido.
     *
     * @param cliente Nombre del cliente.
     * @param destino Destino del pedido (Mesa o Delivery). No puede ser nulo.
     * @return El pedido persistido con su ID generado.
     */
    public Pedido registrarPedido(String cliente, Destino destino) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (destino == null) {
            throw new IllegalArgumentException("Debes asignar un destino válido");
        }
        // El constructor garantiza estado=PENDIENTE, fecha=now(), total=0
        Pedido nuevoPedido = new Pedido(cliente, destino);
        return pedidoRepository.save(nuevoPedido);
    }

    /**
     * Elimina un pedido por su ID.
     *
     * @param id ID del pedido a eliminar.
     * @return true si fue eliminado correctamente.
     * @throws IllegalArgumentException si no existe un pedido con ese ID.
     */
    public Boolean eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el pedido con ID: " + id);
        }
        pedidoRepository.deleteById(id);
        return true;
    }

    /**
     * Edita los campos mutables de un pedido existente.
     *
     * @param id           ID del pedido a editar.
     * @param nuevoNombre  Nuevo nombre del cliente.
     * @param nuevoEstado  Nuevo estado del pedido (tipo EstadoPedido).
     * @param nuevoDestino Nuevo destino del pedido.
     * @return true si se encontró y actualizó, false si no existe el ID.
     */
    public boolean editarPedido(Long id, String nuevoNombre, EstadoPedido nuevoEstado, Destino nuevoDestino) {
        return pedidoRepository.findById(id).map(ped -> {
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nuevo nombre no puede estar vacío");
            }
            if (nuevoDestino == null) {
                throw new IllegalArgumentException("Debes asignar un destino válido");
            }
            ped.setCliente(nuevoNombre);
            ped.setEstado(nuevoEstado);
            ped.setDestino(nuevoDestino);
            pedidoRepository.save(ped);
            return true;
        }).orElse(false);
    }

    /**
     * Retorna todos los pedidos registrados.
     *
     * @return Lista de pedidos.
     * @throws IllegalArgumentException si no hay pedidos en la base de datos.
     */
    public List<Pedido> mostrarPedido() {
        List<Pedido> lista = pedidoRepository.findAll();
        if (lista.isEmpty()) {
            throw new IllegalArgumentException("No se encuentran pedidos en la base de datos");
        }
        return lista;
    }
}
