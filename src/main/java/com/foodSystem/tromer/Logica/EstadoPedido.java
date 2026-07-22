package com.foodSystem.tromer.Logica;

/**
 * Ciclo de vida de un Pedido.
 * Los estados avanzan secuencialmente: PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO.
 * CANCELADO es un estado terminal alcanzable desde cualquier estado anterior a ENTREGADO.
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_PREPARACION,
    LISTO,
    ENTREGADO,
    CANCELADO
}
