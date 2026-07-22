package com.foodSystem.tromer.DTO;

/**
 * DTO de salida para Producto.
 * Expone solo los campos necesarios para el cliente, sin referencias
 * a la entidad JPA ni a relaciones lazy que pudieran causar problemas
 * de serialización.
 */
public record ProductoResponseDTO(
    Long id,
    String nombre,
    String categoria,
    Double precio
) {}
