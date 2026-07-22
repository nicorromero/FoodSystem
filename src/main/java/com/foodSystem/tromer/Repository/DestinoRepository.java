package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la jerarquía de entidades Destino (Mesa y Delivery).
 * Permite buscar destinos por ID para asociarlos a Pedidos en PedidoService.
 */
@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
}
