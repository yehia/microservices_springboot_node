package br.com.sales.productapi.modules.products.dto;

import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.model.Category;
import br.com.sales.productapi.modules.suppliers.dto.SupplierResponse;
import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Integer quantityAvailable;
    private Integer categoryId;
    private Integer supplierId;
}
