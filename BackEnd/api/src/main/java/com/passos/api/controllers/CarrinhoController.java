package com.passos.api.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.produto.Produto;
import com.passos.api.service.CarrinhoService;
import com.passos.api.service.PedidoService;
import com.passos.api.service.ProdutoService;
// import com.passos.api.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    private final CarrinhoService carrinhoService;
    private final ProdutoService produtoService;
    // private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    @GetMapping
    public String verCarrinho(@RequestParam Long usuarioId, Model model) {
        List<ItemPedido> itens = carrinhoService.listarCarrinho(usuarioId);
        model.addAttribute("itens", itens);
        model.addAttribute("total", carrinhoService.calcularTotal(usuarioId));

        return "carrinho";
    }

    @PostMapping("/adicionar")
    public String adicionar(@RequestParam Long usuarioId,
                            @RequestParam Long produtoId,
                            @RequestParam int quantidade) {

        Produto produto = produtoService.buscarPorId(produtoId);
        carrinhoService.adicionarAoCarrinho(usuarioId, produto, quantidade);
        return "redirect:/carrinho?usuarioId=" + usuarioId;
    }

    @PostMapping("/remover")
    public String remover(@RequestParam Long itemId,
                      @RequestParam Long usuarioId) {
    carrinhoService.removerItem(usuarioId, itemId);
    return "redirect:/carrinho?usuarioId=" + usuarioId;
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(@RequestParam Long usuarioId, Model model) {
    try {
        pedidoService.finalizarPedido(usuarioId);
        model.addAttribute("mensagem", "Pedido finalizado com sucesso!");
        return "pedido-confirmado";
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao finalizar pedido: " + e.getMessage());
        return "carrinho";
    }
    }



    @PostMapping("/limpar")
    public String limpar(@RequestParam Long usuarioId) {
        carrinhoService.limparCarrinho(usuarioId);
        return "redirect:/carrinho?usuarioId=" + usuarioId;
    }
}
