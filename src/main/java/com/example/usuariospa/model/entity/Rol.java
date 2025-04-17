package com.example.usuariospa.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Roles disponibles para los usuarios en el sistema")
public enum Rol {
    @Schema(description = "Administrador con acceso total al sistema")
    ADMIN,
    @Schema(description = "Usuario regular con acceso básico")
    USUARIO,
    @Schema(description = "Moderador con acceso intermedio")
    MODERADOR
}