package com.passos.api.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.passos.api.models.usuario.Usuario;
import com.passos.api.service.UsuarioService;

public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService{
    
     @Autowired
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;


    // Construtor com injeção de dependências para o UsuarioService e PasswordEncoder
    // @Autowired
    public UserDetailsServiceImpl(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

        


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Procurando o usuário pelo email
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        // Retornando o UserDetails com o email, senha e as permissões/roles do usuário
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                //.roles(usuario.getRole())  // Adicione a role do usuário aqui
                .build();
    }
}
