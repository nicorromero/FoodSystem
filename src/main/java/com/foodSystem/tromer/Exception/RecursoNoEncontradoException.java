package com.foodSystem.tromer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando un recurso (Producto, Pedido, Reserva, etc.)
 * no se encuentra en la base de datos.
 * Mapeada automáticamente a HTTP 404 Not Found por @ResponseStatus.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Para mensajes de error genéricos (ej: "No hay productos registrados"). */
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    /** Para búsquedas por ID: genera "Producto con ID 5 no encontrado/a". */
    public RecursoNoEncontradoException(String recurso, Long id) {
        super(recurso + " con ID " + id + " no encontrado/a");
    }
}
