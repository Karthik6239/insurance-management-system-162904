package com.example.insurancemanagementbackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Basic endpoints to verify application startup and provide quick info/doc links.
 */
// PUBLIC_INTERFACE
@RestController
@Tag(name = "Hello Controller", description = "Basic endpoints for insurancemanagementbackend")
public class HelloController {

    /**
     * Root endpoint providing a welcome message.
     * @return welcome text
     */
    @GetMapping("/")
    @Operation(summary = "Welcome endpoint", description = "Returns a welcome message")
    public String hello() {
        return "Hello, Spring Boot! Welcome to insurancemanagementbackend";
    }

    /**
     * Redirects to Swagger UI documentation.
     * @return redirect view to swagger UI
     */
    @GetMapping("/docs")
    @Operation(summary = "API Documentation", description = "Redirects to Swagger UI")
    public RedirectView docs() {
        return new RedirectView("/swagger-ui.html");
    }

    /**
     * Simple health check endpoint.
     * @return OK on healthy app
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public String health() {
        return "OK";
    }

    /**
     * Application info endpoint.
     * @return info string
     */
    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information")
    public String info() {
        return "Spring Boot Application: insurancemanagementbackend";
    }
}
