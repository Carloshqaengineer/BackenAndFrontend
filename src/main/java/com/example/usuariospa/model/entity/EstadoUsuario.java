package com.example.usuariospa.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados posibles de un usuario en el sistema")
public enum EstadoUsuario {
    @Schema(description = "Usuario activo y con acceso completo")
    ACTIVO, 
    @Schema(description = "Usuario bloqueado temporalmente")
    BLOQUEADO, 
    @Schema(description = "Usuario suspendido indefinidamente")
    SUSPENDIDO
}