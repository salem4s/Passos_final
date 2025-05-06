package com.passos.api.models.itemPedido;

import com.passos.api.models.Carrinho.Carrinho;
import com.passos.api.models.Carrinho.Carrinho.CarrinhoBuilder;
import com.passos.api.models.pedido.Pedido;
import com.passos.api.models.produto.Produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    private int quantidade;
    private double precoUnitario;

    @ManyToOne
    private Carrinho carrinho;
        

    // Retorna o subtotal como double
    public double getSubtotal() {
        return produto.getPreco() * quantidade;  // Assumindo que 'preco' é do tipo double
    }

    




  
}
