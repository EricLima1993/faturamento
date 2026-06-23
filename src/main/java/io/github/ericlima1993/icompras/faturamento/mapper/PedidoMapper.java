package io.github.ericlima1993.icompras.faturamento.mapper;

import io.github.ericlima1993.icompras.faturamento.model.Cliente;
import io.github.ericlima1993.icompras.faturamento.model.ItemPedido;
import io.github.ericlima1993.icompras.faturamento.model.Pedido;
import io.github.ericlima1993.icompras.faturamento.subscriber.dto.ItemPedidoRepresentationDTO;
import io.github.ericlima1993.icompras.faturamento.subscriber.dto.PedidoRepresentationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(PedidoRepresentationDTO representation) {
        Cliente cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.logradouro(),
                representation.numero(),
                representation.bairro(),
                representation.email(),
                representation.telefone()
        );

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();

        return new Pedido(representation.codigo(), cliente, representation.dataPedido(), representation.total(), itens);

    }

    private ItemPedido mapItem(ItemPedidoRepresentationDTO representation) {
        return new ItemPedido(representation.codigoProduto(), representation.nome(), representation.valorUnitario(), representation.quantidade(), representation.total());
    }
}
