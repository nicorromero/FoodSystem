package com.foodSystem.tromer.DTO;

import java.time.LocalDateTime;

/**
 * DTO de salida para Pedido.
 * Incluye información del destino aplanada (id + nombre) para evitar
 * serialización de la jerarquía de herencia JPA (Destino → Mesa/Delivery),
 * que causaría LazyInitializationException o recursión infinita con Jackson.
 */
public record PedidoResponseDTO(
    Long id,
    String cliente,
    String estado,
    double total,
    LocalDateTime fecha,
    Long destinoId,
    String destinoNombre
) {}
