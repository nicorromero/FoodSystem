package com.foodSystem.tromer.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para operaciones de creación y edición de Producto.
 * Usa Java Record para inmutabilidad automática.
 * Las anotaciones @Valid del Controller activan estas restricciones.
 */
public record ProductoRequestDTO(

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    @NotBlank(message = "La categoría es obligatoria (PLATO, BEBIDA, POSTRE)")
    String categoria,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor mayor a 0")
    Double precio

) {}
