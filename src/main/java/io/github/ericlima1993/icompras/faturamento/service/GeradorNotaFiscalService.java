package io.github.ericlima1993.icompras.faturamento.service;

import io.github.ericlima1993.icompras.faturamento.bucket.BucketFile;
import io.github.ericlima1993.icompras.faturamento.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;

    public void gerar(Pedido pedido) {
        log.info("Gerando a nota fiscal do pedido: {}", pedido.codigo());
        try{
            byte[] byteArray = notaFiscalService.gerarNota(pedido);
            String nomeArquivo = String.format("nota_fiscal_pedido_%d.pdf", pedido.codigo());
            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);
            log.info("Gerada a nota fiscal do pedido: {}, nome do arquivo: {}", pedido.codigo(), nomeArquivo);

            bucketService.upload(file);
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
