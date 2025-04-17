package com.example.usuariospa.controller;

import com.example.usuariospa.model.entity.Usuario;
import com.example.usuariospa.model.entity.EstadoUsuario;
import com.example.usuariospa.model.entity.Rol;
import com.example.usuariospa.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        log.info("Creando nuevo usuario: {}", usuario.getCorreo());
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        log.info("Usuario creado exitosamente con ID: {}", nuevoUsuario.getId());
        return ResponseEntity.ok(nuevoUsuario);
    }

    @Operation(summary = "Asignar grupo a usuario", description = "Asigna un grupo de trabajo a un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grupo asignado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario o grupo no encontrado")
    })
    @PutMapping("/{id}/grupo/{idGrupo}")
    public ResponseEntity<Usuario> asignarGrupo(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "ID del grupo") @PathVariable Long idGrupo) {
        log.info("Asignando grupo {} al usuario {}", idGrupo, id);
        Usuario usuario = usuarioService.asignarGrupo(id, idGrupo);
        log.info("Grupo asignado exitosamente al usuario {}", id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Cambiar rol de usuario", description = "Modifica el rol de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol cambiado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}/rol")
    public ResponseEntity<Usuario> cambiarRol(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Nuevo rol del usuario") @RequestParam Rol rol) {
        log.info("Cambiando rol del usuario {} a {}", id, rol);
        Usuario usuario = usuarioService.cambiarRol(id, rol);
        log.info("Rol cambiado exitosamente para el usuario {}", String.valueOf(id).replace('\n', '_').replace('\r', '_'));
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Cambiar estado de usuario", description = "Modifica el estado de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado cambiado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstado(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del usuario") @RequestParam EstadoUsuario estado) {
        log.info("Cambiando estado del usuario {} a {}", id, estado);
        Usuario usuario = usuarioService.cambiarEstado(id, estado);
        log.info("Estado cambiado exitosamente para el usuario {}", id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Editar datos de usuario", description = "Modifica los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos actualizados exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarDatos(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @RequestBody Usuario usuario) {
        log.info("Editando datos del usuario {}", id);
        Usuario usuarioActualizado = usuarioService.editarDatos(id, usuario);
        log.info("Datos actualizados exitosamente para el usuario {}", id);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        log.info("Eliminando usuario {}", id);
        usuarioService.eliminarUsuario(id);
        log.info("Usuario {} eliminado exitosamente", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar usuarios", description = "Busca usuarios según diferentes criterios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> filtrar(
            @Parameter(description = "Nombre del usuario") @RequestParam(required = false) String nombre,
            @Parameter(description = "Apellido del usuario") @RequestParam(required = false) String apellido,
            @Parameter(description = "Correo del usuario") @RequestParam(required = false) String correo,
            @Parameter(description = "Edad del usuario") @RequestParam(required = false) Integer edad) {
        log.info("Filtrando usuarios por nombre: {}, apellido: {}, correo: {}, edad: {}", 
                nombre, apellido, correo, edad);
        List<Usuario> usuarios = usuarioService.filtrar(nombre, apellido, correo, edad);
        log.info("Se encontraron {} usuarios que coinciden con los criterios de búsqueda", usuarios.size());
        return ResponseEntity.ok(usuarios);
    }
}
