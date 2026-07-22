package com.foodSystem.tromer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para toda la API REST.
 * Centraliza la traducción de excepciones a respuestas HTTP semánticas,
 * eliminando la necesidad de bloques try-catch en los Controllers.
 *
 * Mapeo de excepciones:
 * - RecursoNoEncontradoException  → HTTP 404 Not Found
 * - ValidacionException            → HTTP 400 Bad Request
 * - IllegalArgumentException       → HTTP 400 Bad Request
 * - MethodArgumentNotValidException → HTTP 400 Bad Request (errores de @Valid)
 * - Exception (fallback)           → HTTP 500 Internal Server Error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RecursoNoEncontradoException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorBody("No encontrado", ex.getMessage()));
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Map<String, String>> handleValidacion(ValidacionException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorBody("Error de validación", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorBody("Argumento inválido", ex.getMessage()));
    }

    /**
     * Captura los errores generados por @Valid en los @RequestBody.
     * Retorna un mapa campo → mensaje de error para respuestas detalladas.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleBeanValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erroresCampos = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Valor inválido",
                (v1, v2) -> v1
            ));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Validación fallida");
        body.put("campos", erroresCampos);
        return ResponseEntity.badRequest().body(body);
    }

    /** Fallback para excepciones no manejadas explícitamente. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorBody("Error interno del servidor", ex.getMessage()));
    }

    private Map<String, String> errorBody(String tipo, String detalle) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("error", tipo);
        body.put("detalle", detalle);
        return body;
    }
}
