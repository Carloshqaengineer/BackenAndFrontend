package com.example.usuariospa.repository;

import com.example.usuariospa.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByApellidoContainingIgnoreCase(String apellido);
    List<Usuario> findByCorreoContainingIgnoreCase(String correo);
    List<Usuario> findByEdad(Integer edad);
    Optional<Usuario> findByCorreo(String correo);
}