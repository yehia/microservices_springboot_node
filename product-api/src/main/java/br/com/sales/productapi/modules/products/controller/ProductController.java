package br.com.sales.productapi.modules.products.controller;

import br.com.sales.productapi.modules.products.dto.ProductRequest;
import br.com.sales.productapi.modules.products.dto.ProductResponse;
import br.com.sales.productapi.modules.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return this.productService.save(request);
    }
}
