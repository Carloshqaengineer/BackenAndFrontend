package com.example.usuariospa.service;

import com.example.usuariospa.model.entity.Usuario;
import com.example.usuariospa.model.entity.EstadoUsuario;
import com.example.usuariospa.model.entity.Rol;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
public interface UsuarioService {
    Usuario crearUsuario(@Valid Usuario usuario);
    Usuario asignarGrupo(Long idUsuario, Long idGrupo);
    Usuario cambiarRol(Long idUsuario, Rol nuevoRol);
    Usuario editarDatos(Long idUsuario, @Valid Usuario nuevosDatos);
    void eliminarUsuario(Long id);
    Usuario cambiarEstado(Long idUsuario, EstadoUsuario nuevoEstado);
    List<Usuario> filtrar(String nombre, String apellido, String correo, Integer edad);
    Usuario obtenerPorId(Long id);
}
