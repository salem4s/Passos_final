package com.passos.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.passos.api.models.produto.Produto;
import com.passos.api.models.usuario.Usuario;
import com.passos.api.service.ProdutoService;
import com.passos.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {
    
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    @GetMapping("/")
    public String mostrarIndex(Model model, @RequestParam Long usuarioId) {
        // Buscar o usuário
        Optional<Usuario> usuario = usuarioService.buscarPorId(usuarioId);
        
        // Recuperar todos os produtos disponíveis
        List<Produto> produtos = produtoService.listarTodos();

        // Adicionar usuário e lista de produtos ao modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("produtos", produtos);

        return "index";  // Retorna a página index.html
    } 

        @GetMapping("/index")
        public String exibicao() {
            return "index";
    }

    @GetMapping("/masculino")
    public String masculino() {
        return "masculino"; // Thymeleaf vai procurar templates/masculino.html
    }

    @GetMapping("/feminino")
    public String feminino() {
        return "feminino"; // Thymeleaf vai procurar templates/feminino.html
    }

   

}
