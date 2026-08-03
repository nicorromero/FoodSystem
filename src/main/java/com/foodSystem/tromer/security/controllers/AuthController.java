package com.foodSystem.tromer.security.controllers;

import com.foodSystem.tromer.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Para permitir llamadas desde cualquier lugar
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        // Aquí deberías validar el username y password contra tu BD
        // Por ahora, generamos un token directo
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(token);
    }

    // Clase simple para recibir el pedido
    static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
