package com.foodSystem.tromer.Logica;

import java.util.Arrays;

/**
 * Categorías válidas para un Producto.
 */
public enum Categoria {
    POSTRE,
    PLATO,
    BEBIDA;

    /**
     * Convierte un String a su valor de Categoria correspondiente.
     *
     * @param texto El nombre de la categoría (insensible a mayúsculas/minúsculas).
     * @return La instancia de Categoria correspondiente.
     * @throws IllegalArgumentException si el texto es nulo, vacío o no corresponde a ningún valor.
     */
    public static Categoria desdeString(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                "La categoría no puede ser nula o vacía. Valores válidos: " +
                Arrays.toString(Categoria.values())
            );
        }
        try {
            return Categoria.valueOf(texto.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Categoría inválida: '" + texto + "'. Valores válidos: " +
                Arrays.toString(Categoria.values())
            );
        }
    }
}
