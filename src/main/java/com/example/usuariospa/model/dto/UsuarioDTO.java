package com.example.usuariospa.model.dto;

import com.example.usuariospa.model.entity.EstadoUsuario;
import com.example.usuariospa.model.entity.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO para transferencia de datos de Usuario.
 * Incluye validaciones y anotaciones para formato JSON.
 */
@Data
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150")
    private Integer edad;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100)
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 100)
    private String provincia;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(M|F|Otro)$", message = "El sexo debe ser M, F u Otro")
    private String sexo;

    @Size(max = 100)
    private String profesion;

    @NotNull(message = "El estado es obligatorio")
    private EstadoUsuario estado;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    private Long grupoTrabajoId;
}