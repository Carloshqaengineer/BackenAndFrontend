package com.example.usuariospa.service;

import com.example.usuariospa.exception.ResourceNotFoundException;
import com.example.usuariospa.model.entity.Usuario;
import com.example.usuariospa.model.entity.EstadoUsuario;
import com.example.usuariospa.model.entity.Rol;
import com.example.usuariospa.repository.UsuarioRepository;
import com.example.usuariospa.repository.GrupoTrabajoRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

/**
 * Implementación del servicio de usuarios.
 * Proporciona la lógica de negocio para gestionar usuarios.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final GrupoTrabajoRepository grupoTrabajoRepository;

    @Override
    @Transactional
    public Usuario crearUsuario(@Valid Usuario usuario) {
        log.debug("Iniciando creación de usuario con correo: {}", usuario.getCorreo());
        try {
            // Validar que el correo no exista
            if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
                throw new ValidationException("Ya existe un usuario con el correo: " + usuario.getCorreo());
            }

            // Asegurar valores por defecto
            if (usuario.getEstado() == null) {
                usuario.setEstado(EstadoUsuario.ACTIVO);
            }
            if (usuario.getRol() == null) {
                usuario.setRol(Rol.USUARIO);
            }

            Usuario nuevoUsuario = usuarioRepository.save(usuario);
            log.debug("Usuario creado exitosamente con ID: {}", nuevoUsuario.getId());
            return nuevoUsuario;
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al crear usuario: {}", e.getMessage());
            throw new ValidationException("Error al crear usuario: datos inválidos");
        }
    }

    @Override
    @Transactional
    public Usuario asignarGrupo(Long idUsuario, Long idGrupo) {
        log.debug("Asignando grupo {} al usuario {}", idGrupo, idUsuario);
        Objects.requireNonNull(idUsuario, "El ID de usuario no puede ser nulo");
        Objects.requireNonNull(idGrupo, "El ID de grupo no puede ser nulo");

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        usuario.setGrupoTrabajo(grupoTrabajoRepository.findById(idGrupo)
            .orElseThrow(() -> new ResourceNotFoundException("Grupo de trabajo no encontrado con ID: " + idGrupo)));

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.debug("Grupo asignado exitosamente al usuario {}", idUsuario);
        return usuarioActualizado;
    }

    @Override
    @Transactional
    public Usuario cambiarRol(Long idUsuario, Rol nuevoRol) {
        log.debug("Cambiando rol a {} para usuario {}", nuevoRol, idUsuario);
        Objects.requireNonNull(idUsuario, "El ID de usuario no puede ser nulo");
        Objects.requireNonNull(nuevoRol, "El nuevo rol no puede ser nulo");

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        usuario.setRol(nuevoRol);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.debug("Rol cambiado exitosamente para usuario {}", idUsuario);
        return usuarioActualizado;
    }

    @Override
    @Transactional
    public Usuario editarDatos(Long idUsuario, @Valid Usuario nuevosDatos) {
        log.debug("Editando datos del usuario {}", idUsuario);
        Objects.requireNonNull(idUsuario, "El ID de usuario no puede ser nulo");
        Objects.requireNonNull(nuevosDatos, "Los nuevos datos no pueden ser nulos");

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        // Validar que el nuevo correo no exista si se está cambiando
        if (!usuario.getCorreo().equals(nuevosDatos.getCorreo()) &&
            usuarioRepository.findByCorreo(nuevosDatos.getCorreo()).isPresent()) {
            throw new ValidationException("Ya existe un usuario con el correo: " + nuevosDatos.getCorreo());
        }

        // Actualizar datos
        usuario.setNombre(nuevosDatos.getNombre());
        usuario.setApellido(nuevosDatos.getApellido());
        usuario.setCorreo(nuevosDatos.getCorreo());
        usuario.setCiudad(nuevosDatos.getCiudad());
        usuario.setProvincia(nuevosDatos.getProvincia());
        usuario.setSexo(nuevosDatos.getSexo());
        usuario.setProfesion(nuevosDatos.getProfesion());
        usuario.setFechaNacimiento(nuevosDatos.getFechaNacimiento());
        usuario.setEdad(nuevosDatos.getEdad());

        try {
            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            log.debug("Datos actualizados exitosamente para usuario {}", idUsuario);
            return usuarioActualizado;
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al actualizar usuario: {}", e.getMessage());
            throw new ValidationException("Error al actualizar usuario: datos inválidos");
        }
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        log.debug("Eliminando usuario {}", id);
        Objects.requireNonNull(id, "El ID de usuario no puede ser nulo");

        try {
            if (!usuarioRepository.existsById(id)) {
                throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
            }
            usuarioRepository.deleteById(id);
            log.debug("Usuario {} eliminado exitosamente", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Error al eliminar usuario {}: {}", id, e.getMessage());
            throw new ValidationException("No se puede eliminar el usuario debido a referencias existentes");
        }
    }

    @Override
    @Transactional
    public Usuario cambiarEstado(Long idUsuario, EstadoUsuario nuevoEstado) {
        log.debug("Cambiando estado a {} para usuario {}", nuevoEstado, idUsuario);
        Objects.requireNonNull(idUsuario, "El ID de usuario no puede ser nulo");
        Objects.requireNonNull(nuevoEstado, "El nuevo estado no puede ser nulo");

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        usuario.setEstado(nuevoEstado);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.debug("Estado cambiado exitosamente para usuario {}", idUsuario);
        return usuarioActualizado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> filtrar(String nombre, String apellido, String correo, Integer edad) {
        log.debug("Filtrando usuarios por nombre: {}, apellido: {}, correo: {}, edad: {}", 
                nombre, apellido, correo, edad);

        List<Usuario> usuarios;
        if (nombre != null && !nombre.trim().isEmpty()) {
            usuarios = usuarioRepository.findByNombreContainingIgnoreCase(nombre.trim());
        } else if (apellido != null && !apellido.trim().isEmpty()) {
            usuarios = usuarioRepository.findByApellidoContainingIgnoreCase(apellido.trim());
        } else if (correo != null && !correo.trim().isEmpty()) {
            usuarios = usuarioRepository.findByCorreoContainingIgnoreCase(correo.trim());
        } else if (edad != null) {
            if (edad < 0 || edad > 150) {
                throw new ValidationException("La edad debe estar entre 0 y 150 años");
            }
            usuarios = usuarioRepository.findByEdad(edad);
        } else {
            usuarios = usuarioRepository.findAll();
        }

        log.debug("Se encontraron {} usuarios que coinciden con los criterios de búsqueda", usuarios.size());
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerPorId(Long id) {
        log.debug("Buscando usuario con ID: {}", id);
        Objects.requireNonNull(id, "El ID de usuario no puede ser nulo");
        
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
