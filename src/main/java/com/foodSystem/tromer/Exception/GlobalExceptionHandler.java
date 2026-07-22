package com.foodSystem.tromer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para toda la API REST.
 * Utiliza el estándar RFC 9457 (Problem Details for HTTP APIs) nativo de Spring Boot 3.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail handleNotFound(RecursoNoEncontradoException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso no encontrado");
        problem.setType(URI.create("https://api.foodsystem.com/errors/not-found"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    @ExceptionHandler(ValidacionException.class)
    public ProblemDetail handleValidacion(ValidacionException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Error de validación de negocio");
        problem.setType(URI.create("https://api.foodsystem.com/errors/business-validation"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArg(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Argumento inválido");
        problem.setType(URI.create("https://api.foodsystem.com/errors/invalid-argument"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    /**
     * Captura los errores generados por @Valid en los @RequestBody.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleBeanValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "La estructura de los datos enviados es inválida");
        problem.setTitle("Error de validación de campos");
        problem.setType(URI.create("https://api.foodsystem.com/errors/field-validation"));
        problem.setProperty("timestamp", LocalDateTime.now());

        Map<String, String> erroresCampos = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Valor inválido",
                (v1, v2) -> v1
            ));
        
        problem.setProperty("invalid_params", erroresCampos);
        return problem;
    }

    /** Fallback para excepciones no manejadas explícitamente. */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado. Contacte con soporte.");
        problem.setTitle("Error interno del servidor");
        problem.setType(URI.create("https://api.foodsystem.com/errors/internal-error"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }
}
