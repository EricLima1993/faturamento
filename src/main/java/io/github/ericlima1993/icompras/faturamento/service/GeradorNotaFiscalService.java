package io.github.ericlima1993.icompras.faturamento.service;

import io.github.ericlima1993.icompras.faturamento.bucket.BucketFile;
import io.github.ericlima1993.icompras.faturamento.model.Pedido;
import io.github.ericlima1993.icompras.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;
    private final FaturamentoPublisher faturamentoPublisher;

    public void gerar(Pedido pedido) {
        log.info("Gerando a nota fiscal do pedido: {}", pedido.codigo());
        try{
            byte[] byteArray = notaFiscalService.gerarNota(pedido);
            String nomeArquivo = String.format("nota_fiscal_pedido_%d.pdf", pedido.codigo());
            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);
            log.info("Gerada a nota fiscal do pedido: {}, nome do arquivo: {}", pedido.codigo(), nomeArquivo);

            bucketService.upload(file);

            String url = bucketService.getUrl(nomeArquivo);
            faturamentoPublisher.publicar(pedido, url);

        }catch(JacksonException e){
            log.error("Erro ao processar o json", e);
        }catch(RuntimeException e){
            log.error("Erro técnico ao publicar no tópico de pedidos", e);
        }
    }
}
