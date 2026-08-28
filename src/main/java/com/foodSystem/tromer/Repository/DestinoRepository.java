package com.foodSystem.tromer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.foodSystem.tromer.model.Destino;

/**
 * Repositorio para la jerarquía de entidades Destino (Mesa y Delivery).
 * Permite buscar destinos por ID para asociarlos a Pedidos en PedidoService.
 */
public interface DestinoRepository extends JpaRepository<Destino, Long> {
}
