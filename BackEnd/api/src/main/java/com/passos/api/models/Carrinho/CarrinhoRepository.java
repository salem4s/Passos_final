package com.passos.api.models.Carrinho;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.passos.api.models.usuario.Usuario;

public interface CarrinhoRepository extends JpaRepository<Carrinho,Long>{
        Optional<Carrinho> findByUsuario(Usuario usuario);
}
