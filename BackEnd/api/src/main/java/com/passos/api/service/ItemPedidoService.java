package com.passos.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.itemPedido.ItemPedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {
    
private  ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> salvarTodos(List<ItemPedido> itens) {
        return itemPedidoRepository.saveAll(itens);
    }

    public void removerPorId(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

}
