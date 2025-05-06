package com.passos.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.passos.api.models.produto.Produto;
import com.passos.api.models.produto.ProdutoRepository;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //injeta dependências finais via construtor.
@Embeddable
@Data
public class ProdutoService {
    

     private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void remover(Long id) {
        
        throw new UnsupportedOperationException("Remover");
    }

}
