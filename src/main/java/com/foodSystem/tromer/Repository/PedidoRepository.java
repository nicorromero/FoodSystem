package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.EstadoPedido;
import com.foodSystem.tromer.Logica.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca pedidos de un cliente con paginación.
     * Evita cargar todo el historial de un cliente en memoria de una sola vez.
     * Uso: pedidoRepository.findByClienteIgnoreCase("Juan",
     *          PageRequest.of(0, 20, Sort.by("fecha").descending()));
     */
    Page<Pedido> findByClienteIgnoreCase(String cliente, Pageable pageable);

    /**
     * Filtra pedidos por estado con paginación.
     * Delega el filtrado a la BD (que usa índices) en lugar de filtrar en Java.
     * Útil para el panel de cocina: listar solo pedidos PENDIENTES o EN_PREPARACION.
     */
    Page<Pedido> findByEstado(EstadoPedido estado, Pageable pageable);

    /**
     * Filtro combinado: pedidos de un cliente en un estado concreto.
     * Útil para consultas como "¿cuántos pedidos tiene Juan que están PENDIENTES?".
     */
    List<Pedido> findByClienteIgnoreCaseAndEstado(String cliente, EstadoPedido estado);

    /**
     * Cuenta pedidos en un estado dado sin cargar los objetos en memoria.
     * Ideal para dashboards, alertas y métricas en tiempo real.
     */
    long countByEstado(EstadoPedido estado);
}
