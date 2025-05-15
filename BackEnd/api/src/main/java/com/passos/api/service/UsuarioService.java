package com.passos.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.passos.api.models.usuario.Usuario;
import com.passos.api.models.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    @Autowired
     private  UsuarioRepository usuarioRepository;
     private final PasswordEncoder passwordEncoder;


     public void cadastrar(String nome, String email, String senha) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(email);

        if (existente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(passwordEncoder.encode(senha)) // Criptografa
                .build();

        usuarioRepository.save(novoUsuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // public Usuario salvar(Usuario usuario) {
    //     // Aqui você pode adicionar criptografia de senha (ex: BCryptPasswordEncoder)
    //     return usuarioRepository.save(usuario);
    // }

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
    

    // public Usuario autenticar(String email, String senha) {
    //     Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
    //     if (usuarioOpt.isPresent()) {
    //         Usuario usuario = usuarioOpt.get();
    //         if (usuario.getSenha().equals(senha)) { // ← Trocar por senha codificada em produção
    //             return usuario;
    //         }
    //     }
    //     return null;
    // }

    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                return usuario;
            }
        }
        return null;
    }
    



    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean emailJaExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}
