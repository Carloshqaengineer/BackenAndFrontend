package com.example.usuariospa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Usuarios SPA API",
        version = "1.0",
        description = "API for managing users in the SPA application"
    )
)
public class UsuariosSpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosSpaApplication.class, args);
    }
}

