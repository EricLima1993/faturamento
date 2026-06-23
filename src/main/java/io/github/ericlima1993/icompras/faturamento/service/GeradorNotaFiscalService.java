package io.github.ericlima1993.icompras.faturamento.service;

import io.github.ericlima1993.icompras.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GeradorNotaFiscalService {

    public void gerar(Pedido pedido) {
        log.info("Gerada a nota fiscal do pedido: {}", pedido.codigo());
    }
}
