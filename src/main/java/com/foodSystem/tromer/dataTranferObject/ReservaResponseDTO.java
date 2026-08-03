package com.foodSystem.tromer.dataTranferObject;

import java.time.LocalDateTime;

/**
 * DTO de salida para Reserva.
 */
public record ReservaResponseDTO(
        Long id,
        String cliente,
        int cantidad,
        LocalDateTime fecha) {
}
