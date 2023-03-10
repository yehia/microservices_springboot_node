package br.com.sales.productapi.modules.products.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.modules.categories.service.CategoryService;
import br.com.sales.productapi.modules.products.dto.ProductRequest;
import br.com.sales.productapi.modules.products.dto.ProductResponse;
import br.com.sales.productapi.modules.products.model.Product;
import br.com.sales.productapi.modules.products.repository.ProductRepository;
import br.com.sales.productapi.modules.suppliers.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    public ProductResponse save(ProductRequest request) {
        validateProductDataInformed(request);
        validateCategoryAndSupplierInformed(request);
        var category = this.categoryService.findById(request.getCategoryId());
        var supplier = this.supplierService.findById(request.getSupplierId());
        var product = this.productRepository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    private void validateProductDataInformed(ProductRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The Product's name was not informed.");
        }

        if(isEmpty(request.getQuantityAvailable())) {
            throw new ValidationException("The Product's quantity was not informed.");
        }

        if(request.getQuantityAvailable() <= ZERO) {
            throw new ValidationException("The Product's quantity can't be less or equal to zero.");
        }
    }

    private void validateCategoryAndSupplierInformed(ProductRequest request) {
        if(isEmpty(request.getCategoryId())) {
            throw  new ValidationException("The Category's id was not informed.");
        }

        if(isEmpty(request.getSupplierId())) {
            throw  new ValidationException("The Supplier's id was not informed.");
        }
    }
}
