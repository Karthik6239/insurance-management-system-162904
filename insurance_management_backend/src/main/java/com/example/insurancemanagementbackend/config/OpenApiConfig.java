package com.example.insurancemanagementbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Insurance Management Backend API.
 */
@Configuration
public class OpenApiConfig {

    // PUBLIC_INTERFACE
    @Bean
    public OpenAPI imsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance Management Backend API")
                        .version("0.1.0")
                        .description("REST API with JWT-based authentication and RBAC"))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI")
                        .url("/swagger-ui.html"));
    }
}
