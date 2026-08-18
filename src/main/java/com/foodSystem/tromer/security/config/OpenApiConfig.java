package com.foodSystem.tromer.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI / Swagger UI.
 * Agrega el esquema de seguridad Bearer JWT para que Swagger muestre
 * el botón "Authorize" y envíe el token en cada request.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("FoodSystem API")
                        .description("REST API para gestión de restaurante: pedidos, reservas y menú")
                        .version("1.0.0"))
                // Aplica el esquema de seguridad globalmente a todos los endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresá el token JWT obtenido del endpoint /api/auth/login")));
    }
}
