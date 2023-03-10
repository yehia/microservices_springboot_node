package br.com.sales.productapi.modules.products.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.config.helpers.SuccessResponse;
import br.com.sales.productapi.modules.categories.service.CategoryService;
import br.com.sales.productapi.modules.products.dto.ProductRequest;
import br.com.sales.productapi.modules.products.dto.ProductResponse;
import br.com.sales.productapi.modules.products.dto.ProductStockDTO;
import br.com.sales.productapi.modules.products.model.Product;
import br.com.sales.productapi.modules.products.repository.ProductRepository;
import br.com.sales.productapi.modules.suppliers.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProductResponse> findAll() {
        return this.productRepository.findAll().stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("The Product's name must be informed");
        }
        return this.productRepository.findByNameIgnoreCaseContaining(name).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if(isEmpty(supplierId)) {
            throw new ValidationException("The Products's Supplier ID must be informed");
        }
        return this.productRepository.findBySupplierId(supplierId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if(isEmpty(categoryId)) {
            throw new ValidationException("The Products's Category ID must be informed");
        }
        return this.productRepository.findByCategoryId(categoryId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(this.findById(id));
    }

    public Product findById(Integer id) {
        this.validateInformedId(id);
        return this.productRepository.findById(id).orElseThrow(() -> new ValidationException("There's no Product for the given ID."));
    }

    public Boolean existsByCategoryId(Integer categoryId) {
        return this.productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId) {
        return this.productRepository.existsBySupplierId(supplierId);
    }

    public ProductResponse save(ProductRequest request) {
        this.validateProductDataInformed(request);
        this.validateCategoryAndSupplierInformed(request);
        var category = this.categoryService.findById(request.getCategoryId());
        var supplier = this.supplierService.findById(request.getSupplierId());
        var product = this.productRepository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        this.validateProductDataInformed(request);
        this.validateInformedId(id);
        this.validateCategoryAndSupplierInformed(request);
        var category = this.categoryService.findById(request.getCategoryId());
        var supplier = this.supplierService.findById(request.getSupplierId());
        var product = Product.of(request, category, supplier);
        product.setId(id);
        this.productRepository.save(product);
        return ProductResponse.of(product);
    }

    public SuccessResponse delete(Integer id) {
        this.validateInformedId(id);
        this.productRepository.deleteById(id);
        return SuccessResponse.create("The Product was deleted.");
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("The Product's ID must be informed.");
        }
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

    public void updateProductStock(ProductStockDTO productStockDTO) {}
}
