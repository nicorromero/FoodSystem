package com.foodSystem.tromer.DTO;

import java.time.LocalDateTime;

/**
 * DTO de salida para Reserva.
 */
public record ReservaResponseDTO(
    Long id,
    String cliente,
    int cantidad,
    LocalDateTime fecha
) {}
