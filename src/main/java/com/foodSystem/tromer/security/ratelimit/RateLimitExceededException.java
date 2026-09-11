package com.foodSystem.tromer.security.ratelimit;

/**
 * Excepción lanzada cuando se excede el límite de intentos de login.
 * Puede ser por:
 * - Demasiadas solicitudes desde la misma IP (Capa 1).
 * - Demasiados intentos fallidos para la misma cuenta (Capa 2).
 *
 * Incluye información para el header Retry-After de la respuesta HTTP.
 */
public class RateLimitExceededException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final long retryAfterSeconds;

    /**
     * @param message           Mensaje descriptivo del bloqueo.
     * @param retryAfterSeconds Segundos que el cliente debe esperar antes de reintentar.
     */
    public RateLimitExceededException(String message, long retryAfterSeconds) {
        super(message);
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public RateLimitExceededException(String message) {
        this(message, 60);
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
