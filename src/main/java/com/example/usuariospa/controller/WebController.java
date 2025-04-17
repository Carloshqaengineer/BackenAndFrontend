package com.example.usuariospa.controller;

import com.example.usuariospa.model.entity.Usuario;
import com.example.usuariospa.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    private final UsuarioService usuarioService;

    public WebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Página principal (lista de usuarios)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("usuarios", usuarioService.filtrar(null, null, null, null));
        return "index";
    }

    // Formulario de creación de usuario
    @GetMapping("/nuevo-usuario")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "nuevo-usuario";
    }

    // Formulario de edición
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        return "editar-usuario";
    }
}