package com.passos.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.passos.api.models.Carrinho.Carrinho;
import com.passos.api.models.Carrinho.CarrinhoRepository;
import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.itemPedido.ItemPedidoRepository;
import com.passos.api.models.produto.Produto;
import com.passos.api.models.usuario.Usuario;
import com.passos.api.models.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public Carrinho getCarrinho(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return carrinhoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrinho novoCarrinho = Carrinho.builder()
                            .usuario(usuario)
                            .build();
                    return carrinhoRepository.save(novoCarrinho);
                });
    }

    public void adicionarAoCarrinho(Long usuarioId, Produto produto, int quantidade) {
        Carrinho carrinho = getCarrinho(usuarioId);

        // Verifica se já existe o item no carrinho
        ItemPedido itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produto.getId()))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            itemPedidoRepository.save(itemExistente);
        } else {
            ItemPedido novoItem = ItemPedido.builder()
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoUnitario(produto.getPreco())
                    .carrinho(carrinho)
                    .build();

            itemPedidoRepository.save(novoItem);
            carrinho.getItens().add(novoItem);
            carrinhoRepository.save(carrinho);
        }
    }

        // public List<ItemPedido> listarCarrinho(Long usuarioId) {
        //     return getCarrinho(usuarioId).getItens();
        // }

        public List<ItemPedido> listarCarrinho (Long usuarioId) {
            // Add null check para usuarioId
            if (usuarioId == null) {
                throw new IllegalArgumentException("ID do usuário não pode ser nulo");
                }
                return getCarrinho(usuarioId).getItens();
        }

        private Carrinho buscarCarrinhoDoUsuario(Long usuarioId) {
            return getCarrinho(usuarioId);
        }
        
        private void salvarCarrinho(Carrinho carrinho) {
            carrinhoRepository.save(carrinho);
        }

        public void removerItem(Long usuarioId, Long itemId) {
            Carrinho carrinho = buscarCarrinhoDoUsuario(usuarioId);
            carrinho.getItens().removeIf(item -> item.getId().equals(itemId));
            salvarCarrinho(carrinho);
        }

        public double calcularTotal(Long usuarioId) {
            Carrinho carrinho = getCarrinho(usuarioId);
            return carrinho.getItens().stream()
                    .mapToDouble(ItemPedido::getSubtotal)
                    .sum();
        }
        
        
    
        public void limparCarrinho(Long usuarioId) {
            Carrinho carrinho = getCarrinho(usuarioId);
            itemPedidoRepository.deleteAll(carrinho.getItens());
            carrinho.getItens().clear();
            carrinhoRepository.save(carrinho);
        }
     


    }

