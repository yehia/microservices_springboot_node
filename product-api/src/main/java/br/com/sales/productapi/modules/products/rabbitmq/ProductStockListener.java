package br.com.sales.productapi.modules.products.rabbitmq;

import br.com.sales.productapi.modules.products.dto.ProductStockDTO;
import br.com.sales.productapi.modules.products.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO productStockDTO) throws JsonProcessingException {
        log.info("Receive msg: {}", new ObjectMapper().writeValueAsString(productStockDTO));
        this.productService.updateProductStock(productStockDTO);
    }
}
