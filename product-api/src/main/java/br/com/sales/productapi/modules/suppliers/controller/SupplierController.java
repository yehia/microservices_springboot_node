package br.com.sales.productapi.modules.suppliers.controller;

import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.suppliers.dto.SupplierRequest;
import br.com.sales.productapi.modules.suppliers.dto.SupplierResponse;
import br.com.sales.productapi.modules.suppliers.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request) {
        return this.supplierService.save(request);
    }

    @GetMapping
    public List<SupplierResponse> findAll() {
        return this.supplierService.findAll();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable Integer id) {
        return this.supplierService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name) {
        return this.supplierService.findByName(name);
    }
}
