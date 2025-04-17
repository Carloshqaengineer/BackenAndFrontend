package com.example.usuariospa.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario en el sistema.
 * Contiene la información básica y estado del usuario.
 */
@Schema(description = "Entidad que representa un usuario en el sistema")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Schema(description = "Identificador único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com", required = true)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(unique = true, nullable = false)
    private String correo;

    @Schema(description = "Ciudad de residencia del usuario", example = "Madrid", required = true)
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100)
    private String ciudad;

    @Schema(description = "Provincia de residencia del usuario", example = "Madrid", required = true)
    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 100)
    private String provincia;

    @Schema(description = "Sexo del usuario (M, F u Otro)", example = "M", required = true)
    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(M|F|Otro)$", message = "El sexo debe ser M, F u Otro")
    private String sexo;

    @Schema(description = "Profesión del usuario", example = "Ingeniero")
    @Size(max = 100)
    private String profesion;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01", required = true)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Schema(description = "Edad del usuario", example = "30", minimum = "0", maximum = "150")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150")
    private Integer edad;

    @Schema(description = "Estado actual del usuario", example = "ACTIVO", required = true)
    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @Schema(description = "Rol del usuario en el sistema", example = "USUARIO", required = true)
    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.USUARIO;

    @Schema(description = "Grupo de trabajo al que pertenece el usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_trabajo_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private GrupoTrabajo grupoTrabajo;

    @Schema(description = "Fecha y hora de creación del registro", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha y hora de última actualización", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "Versión del registro para control de concurrencia", accessMode = Schema.AccessMode.READ_ONLY)
    @Version
    private Long version;
}
