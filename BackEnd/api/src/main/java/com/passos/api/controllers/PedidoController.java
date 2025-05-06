package com.passos.api.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.pedido.Pedido;
import com.passos.api.models.usuario.Usuario;
import com.passos.api.service.CarrinhoService;
import com.passos.api.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {
    

    private final PedidoService pedidoService;
    private final CarrinhoService carrinhoService;

    @PostMapping("/finalizar")
    public String finalizar(@RequestParam Long usuarioId, Model model) {
        List<ItemPedido> itens = carrinhoService.listarCarrinho(usuarioId);
        Usuario usuario = Usuario.builder().id(usuarioId).build(); // mock, usar sessão no futuro
        Pedido pedido = pedidoService.criarPedido(usuario, itens);
        carrinhoService.limparCarrinho(usuarioId);
        model.addAttribute("pedido", pedido);
        return "pedido-confirmado";
    }

    @GetMapping
    public String listar(@RequestParam Long usuarioId, Model model) {
        Usuario usuario = Usuario.builder().id(usuarioId).build(); // mock
        List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(usuario);
        model.addAttribute("pedidos", pedidos);
        return "pedidos";

        
    }
}
