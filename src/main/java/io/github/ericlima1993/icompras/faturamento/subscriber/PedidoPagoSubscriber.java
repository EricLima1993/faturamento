package io.github.ericlima1993.icompras.faturamento.subscriber;

import io.github.ericlima1993.icompras.faturamento.mapper.PedidoMapper;
import io.github.ericlima1993.icompras.faturamento.model.Pedido;
import io.github.ericlima1993.icompras.faturamento.service.GeradorNotaFiscalService;
import io.github.ericlima1993.icompras.faturamento.subscriber.dto.PedidoRepresentationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json){
        try {
            log.info("Recebendo pedidos-pagos: {}", json);
            var representation = objectMapper.readValue(json, PedidoRepresentationDTO.class);
            Pedido pedido = pedidoMapper.map(representation);
            geradorNotaFiscalService.gerar(pedido);

        } catch (JacksonException e) {
            log.error("Erro ao ler os dados de pedido.", e);
        }
    }
}
