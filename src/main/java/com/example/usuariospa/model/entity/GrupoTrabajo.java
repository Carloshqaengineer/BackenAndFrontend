package com.example.usuariospa.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidad que representa un grupo de trabajo")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoTrabajo {
    @Schema(description = "Identificador único del grupo de trabajo", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del grupo de trabajo", example = "Desarrollo", required = true)
    @NotBlank(message = "El nombre del grupo es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
}