package io.github.ericlima1993.icompras.faturamento.publisher.dto;

public record AtualizacaoStatusPedidoDTO(Long codigo, StatusPedido status, String urlNotaFiscal) {
}
