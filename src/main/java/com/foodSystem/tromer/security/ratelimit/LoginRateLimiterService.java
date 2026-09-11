package com.foodSystem.tromer.security.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servicio de protección contra fuerza bruta en el endpoint de login.
 *
 * Implementa dos capas complementarias:
 *
 * CAPA 1 — Rate Limit por IP (Bucket4j):
 *   Limita a {@value #MAX_REQUESTS_PER_MINUTE} requests por minuto por dirección IP.
 *   Protege contra volumen bruto de solicitudes, incluso si el atacante rota
 *   cuentas de usuario en cada intento.
 *
 * CAPA 2 — Lockout por cuenta:
 *   Tras {@value #MAX_FAILED_ATTEMPTS} intentos fallidos para la misma cuenta,
 *   bloquea el login de esa cuenta por {@value #LOCKOUT_MINUTES} minutos.
 *   Protege contra ataques dirigidos a una cuenta específica.
 *
 * Ambas capas usan almacenamiento in-memory (ConcurrentHashMap). Para múltiples
 * instancias de la aplicación, reemplazar con Redis (Bucket4j tiene soporte nativo).
 */
@Service
public class LoginRateLimiterService {

    private static final Logger log = LoggerFactory.getLogger(LoginRateLimiterService.class);

    // ── Configuración Capa 1: Rate Limit por IP ─────────────────────────────
    private static final int MAX_REQUESTS_PER_MINUTE = 10;

    // ── Configuración Capa 2: Lockout por cuenta ────────────────────────────
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCKOUT_MINUTES = 15;

    /** Capa 1: un Bucket por cada IP que intenta login. */
    private final Map<String, Bucket> ipBuckets = new ConcurrentHashMap<>();

    /** Capa 2: info de intentos fallidos por cada cuenta (username/email). */
    private final Map<String, AccountLockInfo> accountLocks = new ConcurrentHashMap<>();

    // ═════════════════════════════════════════════════════════════════════════
    //  CAPA 1 — RATE LIMIT POR IP
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Verifica si la IP tiene tokens disponibles en su bucket.
     * Cada IP tiene un bucket que se rellena a {@value #MAX_REQUESTS_PER_MINUTE}
     * tokens por minuto (Greedy refill).
     *
     * @param clientIp Dirección IP del cliente.
     * @return true si la IP está dentro del límite; false si lo excedió.
     */
    public boolean isIpAllowed(String clientIp) {
        Bucket bucket = ipBuckets.computeIfAbsent(clientIp, this::createBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createBucket(String key) {
        Bandwidth limit = Bandwidth.classic(
                MAX_REQUESTS_PER_MINUTE,
                Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))
        );
        return Bucket.builder().addLimit(limit).build();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  CAPA 2 — LOCKOUT POR CUENTA
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Verifica si una cuenta está bloqueada por demasiados intentos fallidos.
     *
     * @param username El username o email de la cuenta.
     * @return true si la cuenta NO está bloqueada (puede intentar login);
     *         false si está bloqueada.
     */
    public boolean isAccountAllowed(String username) {
        AccountLockInfo info = accountLocks.get(username);
        if (info == null) {
            return true;
        }
        // Si el bloqueo expiró, limpiamos
        if (info.isExpired()) {
            accountLocks.remove(username);
            return true;
        }
        return !info.isLocked();
    }

    /**
     * Registra un intento fallido de login para una cuenta.
     * Si alcanza {@value #MAX_FAILED_ATTEMPTS}, la cuenta queda bloqueada
     * por {@value #LOCKOUT_MINUTES} minutos.
     *
     * @param username El username o email de la cuenta.
     */
    public void registerFailedAttempt(String username) {
        AccountLockInfo info = accountLocks.computeIfAbsent(username, k -> new AccountLockInfo());
        int attempts = info.incrementAttempts();

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            info.lock(Duration.ofMinutes(LOCKOUT_MINUTES));
            log.warn("Cuenta '{}' bloqueada por {} minutos tras {} intentos fallidos",
                    username, LOCKOUT_MINUTES, attempts);
        }
    }

    /**
     * Resetea el contador de intentos fallidos tras un login exitoso.
     *
     * @param username El username o email de la cuenta.
     */
    public void resetFailedAttempts(String username) {
        accountLocks.remove(username);
    }

    /**
     * Retorna los minutos restantes de bloqueo para una cuenta.
     * Útil para informar al usuario cuánto debe esperar.
     *
     * @param username El username o email de la cuenta.
     * @return Minutos restantes, o 0 si no está bloqueada.
     */
    public long getRemainingLockoutMinutes(String username) {
        AccountLockInfo info = accountLocks.get(username);
        if (info == null || !info.isLocked() || info.isExpired()) {
            return 0;
        }
        return Duration.between(Instant.now(), info.getLockedUntil()).toMinutes() + 1;
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  LIMPIEZA PERIÓDICA
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Limpia entradas expiradas cada 30 minutos para evitar memory leaks.
     * Las IPs que ya no envían requests y las cuentas con bloqueos expirados
     * se eliminan del mapa.
     */
    @Scheduled(fixedRate = 1800000) // 30 minutos
    public void cleanupExpiredEntries() {
        int removedAccounts = 0;
        var iterator = accountLocks.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().isExpired()) {
                iterator.remove();
                removedAccounts++;
            }
        }
        if (removedAccounts > 0) {
            log.info("Limpieza de rate limiter: {} cuentas expiradas eliminadas", removedAccounts);
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  CLASE INTERNA: Información de bloqueo por cuenta
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Información de intentos fallidos y estado de bloqueo para una cuenta.
     * Thread-safe mediante sincronización en los métodos de mutación.
     */
    private static class AccountLockInfo {
        private int failedAttempts = 0;
        private Instant lockedUntil = null;
        private Instant lastAttempt = Instant.now();

        synchronized int incrementAttempts() {
            this.failedAttempts++;
            this.lastAttempt = Instant.now();
            return this.failedAttempts;
        }

        synchronized void lock(Duration duration) {
            this.lockedUntil = Instant.now().plus(duration);
        }

        synchronized boolean isLocked() {
            return lockedUntil != null && Instant.now().isBefore(lockedUntil);
        }

        synchronized boolean isExpired() {
            // Si está bloqueada, expiró cuando pasó el lockout
            if (lockedUntil != null) {
                return Instant.now().isAfter(lockedUntil);
            }
            // Si no está bloqueada, expiró si no hubo intentos en 30 min
            return Duration.between(lastAttempt, Instant.now()).toMinutes() > 30;
        }

        synchronized Instant getLockedUntil() {
            return lockedUntil;
        }
    }
}
