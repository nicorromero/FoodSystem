package com.foodSystem.tromer.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO de entrada para operaciones de creación y edición de Reserva.
 */
public record ReservaRequestDTO(

    @NotBlank(message = "El nombre del cliente es obligatorio")
    String cliente,

    @Min(value = 1, message = "La reserva debe ser para al menos 1 persona")
    @Max(value = 50, message = "La reserva no puede ser para más de 50 personas")
    int cantidad,

    @NotNull(message = "La fecha de la reserva es obligatoria")
    @FutureOrPresent(message = "La fecha de la reserva no puede estar en el pasado")
    LocalDateTime fecha

) {}
