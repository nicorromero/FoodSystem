package com.foodSystem.tromer.DTO;

import com.foodSystem.tromer.Logica.EstadoPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para operaciones de creación y edición de Pedido.
 *
 * - Al CREAR: solo se usan 'cliente' y 'destinoId'. El estado se ignora
 *   (siempre nace como PENDIENTE).
 * - Al EDITAR: se usan los tres campos. 'estado' permite cambiar el estado
 *   del pedido (EN_PREPARACION, LISTO, ENTREGADO, CANCELADO).
 */
public record PedidoRequestDTO(

    @NotBlank(message = "El nombre del cliente es obligatorio")
    String cliente,

    @NotNull(message = "El ID del destino es obligatorio (Mesa o Delivery)")
    Long destinoId,

    EstadoPedido estado  // Opcional en creación; requerido en actualización de estado

) {}
