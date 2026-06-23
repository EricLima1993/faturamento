package io.github.ericlima1993.icompras.faturamento.subscriber.dto;

import java.math.BigDecimal;

public record ItemPedidoRepresentationDTO(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal total
) {

}
