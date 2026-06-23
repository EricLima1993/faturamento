package io.github.ericlima1993.icompras.faturamento.subscriber.dto;



import java.math.BigDecimal;
import java.util.List;

public record PedidoRepresentationDTO(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        List<ItemPedidoRepresentationDTO> itens
) {
}
