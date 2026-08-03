package com.foodSystem.tromer.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para APIs stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**") // Protege todas las rutas API
                        .authenticated() // Requiere autenticación (JWT)
                )
                .httpBasic(basic -> basic.disable()) // Desactiva Basic Auth
        // Aquí luego agregaremos el filtro JWT
        ;
        return http.build();
    }
}
