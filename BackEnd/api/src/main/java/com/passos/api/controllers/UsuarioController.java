package com.passos.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.passos.api.models.usuario.Usuario;
import com.passos.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
    
    @Autowired
    private final UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String formCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.emailJaExiste(usuario.getEmail())) {
            model.addAttribute("erro", "Email já cadastrado");
            return "cadastro";
        }
        usuarioService.salvar(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario == null) {
            model.addAttribute("erro", "Email ou senha inválidos");
            return "login";
        }
        model.addAttribute("usuarioLogado", usuario);
        return "redirect:/produtos";
    }
}
