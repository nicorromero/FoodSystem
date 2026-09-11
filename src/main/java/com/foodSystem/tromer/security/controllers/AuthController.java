package com.foodSystem.tromer.security.controllers;

import com.foodSystem.tromer.security.dto.LoginRequest;
import com.foodSystem.tromer.security.jwt.JwtService;
import com.foodSystem.tromer.security.model.User;
import com.foodSystem.tromer.security.ratelimit.LoginRateLimiterService;
import com.foodSystem.tromer.security.ratelimit.RateLimitExceededException;
import com.foodSystem.tromer.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LoginRateLimiterService rateLimiter;

    public AuthController(JwtService jwtService,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          LoginRateLimiterService rateLimiter) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request,
                                        HttpServletRequest httpRequest) {

        String clientIp = getClientIp(httpRequest);

        // ── CAPA 1: Rate Limit por IP ───────────────────────────────────────
        if (!rateLimiter.isIpAllowed(clientIp)) {
            throw new RateLimitExceededException(
                    "Demasiadas solicitudes desde esta IP. Intente nuevamente en 1 minuto.", 60);
        }

        // ── CAPA 2: Verificar lockout por cuenta ────────────────────────────
        if (!rateLimiter.isAccountAllowed(request.getUsername())) {
            long minutesLeft = rateLimiter.getRemainingLockoutMinutes(request.getUsername());
            throw new RateLimitExceededException(
                    "Cuenta bloqueada temporalmente por demasiados intentos fallidos. "
                    + "Intente en " + minutesLeft + " minutos.",
                    minutesLeft * 60);
        }

        // ── Autenticación ───────────────────────────────────────────────────
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            // Registrar fallo aunque el usuario no exista (previene enumeración)
            rateLimiter.registerFailedAttempt(request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            rateLimiter.registerFailedAttempt(request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        // ── Login exitoso: resetear intentos fallidos ───────────────────────
        rateLimiter.resetFailedAttempts(request.getUsername());
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody LoginRequest request,
                                           HttpServletRequest httpRequest) {

        // Rate limit en registro también (previene spam de cuentas)
        String clientIp = getClientIp(httpRequest);
        if (!rateLimiter.isIpAllowed(clientIp)) {
            throw new RateLimitExceededException(
                    "Demasiadas solicitudes desde esta IP. Intente nuevamente en 1 minuto.", 60);
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
    }

    /**
     * Extrae la IP real del cliente, considerando proxies y load balancers.
     * Prioriza el header X-Forwarded-For (que establecen los reverse proxies).
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            // X-Forwarded-For puede tener múltiples IPs: "client, proxy1, proxy2"
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
