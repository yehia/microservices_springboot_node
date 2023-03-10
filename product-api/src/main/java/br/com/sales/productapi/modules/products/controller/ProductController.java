package br.com.sales.productapi.modules.products.controller;

import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.products.dto.ProductRequest;
import br.com.sales.productapi.modules.products.dto.ProductResponse;
import br.com.sales.productapi.modules.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return this.productService.save(request);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        return this.productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return this.productService.findByName(name);
    }

    @GetMapping("supplier/{id}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer id) {
        return this.productService.findBySupplierId(id);
    }

    @GetMapping("category/{id}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer id) {
        return this.productService.findByCategoryId(id);
    }
}
