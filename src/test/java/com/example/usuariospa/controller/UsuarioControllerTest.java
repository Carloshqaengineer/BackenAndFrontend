package com.example.usuariospa.controller;

import com.example.usuariospa.model.entity.Usuario;
import com.example.usuariospa.model.entity.EstadoUsuario;
import com.example.usuariospa.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void editarDatos_DeberiaRetornar200_CuandoActualizacionExitosa() throws Exception {
        // Arrange
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNombre("Test");
        usuario.setCorreo("test@test.com");

        when(usuarioService.editarDatos(eq(userId), any(Usuario.class)))
                .thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    void editarDatos_DeberiaRetornar400_CuandoValidacionFalla() throws Exception {
        // Arrange
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNombre("Test");
        usuario.setCorreo("test@test.com");

        when(usuarioService.editarDatos(eq(userId), any(Usuario.class)))
                .thenThrow(new ValidationException("Error de validación"));

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cambiarEstado_DeberiaRetornar200_CuandoCambioExitoso() throws Exception {
        // Arrange
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        when(usuarioService.cambiarEstado(eq(userId), eq(EstadoUsuario.ACTIVO)))
                .thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(patch("/api/usuarios/{id}/estado", userId)
                .param("estado", "INACTIVO")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void cambiarEstado_DeberiaRetornar400_CuandoEstadoInvalido() throws Exception {
        // Arrange
        Long userId = 1L;

        when(usuarioService.cambiarEstado(eq(userId), any(EstadoUsuario.class)))
                .thenThrow(new ValidationException("Estado inválido"));

        // Act & Assert
        mockMvc.perform(patch("/api/usuarios/{id}/estado", userId)
                .param("estado", "ESTADO_INVALIDO")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}


