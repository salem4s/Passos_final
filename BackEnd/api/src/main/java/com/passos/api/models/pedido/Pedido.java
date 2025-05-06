package com.passos.api.models.pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.produto.Produto;
import com.passos.api.models.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    // private Pedido pedido;

    // @ManyToOne
    // private Produto produto;

    // private int quantidade;
    // private double precoUnitario;

    @ManyToOne
    public Usuario usuario;

    private LocalDateTime data;

    private double total;

    private String status;

    // List<ItemPedido> itens;

     @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    

}
