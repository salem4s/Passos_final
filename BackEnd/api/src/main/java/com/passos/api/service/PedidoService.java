package com.passos.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.pedido.Pedido;
import com.passos.api.models.pedido.PedidoRepository;
import com.passos.api.models.usuario.Usuario;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    

    private final PedidoRepository pedidoRepository;

    @Transactional
    public Pedido criarPedido(Usuario usuario, List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter ao menos um item.");
        }

        double total = itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .data(LocalDateTime.now())
                .total(total)
                .itens(itens)
                .build();

        // Relaciona os itens ao pedido antes de salvar
        itens.forEach(item -> item.setPedido(pedido));

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getUsuario().getId().equals(usuario.getId()))
                .toList();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void finalizarPedido(Long usuarioId) {
        
        throw new UnsupportedOperationException("Unimplemented method 'finalizarPedido'");
    }
}
