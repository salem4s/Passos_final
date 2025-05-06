package com.passos.api.models.Carrinho;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.passos.api.models.itemPedido.ItemPedido;
import com.passos.api.models.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }

    public void removerItem(Long itemId) {
        this.itens.removeIf(item -> item.getId().equals(itemId));
    }

      // Calcular o total do carrinho
      public double calcularTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }


    public List<ItemPedido> getItens() {
        return itens;
    }

    
}
