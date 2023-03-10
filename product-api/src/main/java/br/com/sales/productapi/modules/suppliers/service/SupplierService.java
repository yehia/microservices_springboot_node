package br.com.sales.productapi.modules.suppliers.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.config.helpers.SuccessResponse;
import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.model.Category;
import br.com.sales.productapi.modules.products.service.ProductService;
import br.com.sales.productapi.modules.suppliers.dto.SupplierRequest;
import br.com.sales.productapi.modules.suppliers.dto.SupplierResponse;
import br.com.sales.productapi.modules.suppliers.model.Supplier;
import br.com.sales.productapi.modules.suppliers.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductService productService;

    public List<SupplierResponse> findAll() {
        return this.supplierRepository.findAll().stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("The Supplier's name must be informed");
        }
        return this.supplierRepository.findByNameIgnoreCaseContaining(name).stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(this.findById(id));
    }

    public Supplier findById(Integer id) {
        this.validateInformedId(id);
        return this.supplierRepository.findById(id).orElseThrow(() -> new ValidationException("There's no Supplier for the given ID"));
    }

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier = this.supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id) {
        this.validateSupplierNameInformed(request);
        this.validateInformedId(id);
        var supplier = Supplier.of(request);
        supplier.setId(id);
        this.supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    public SuccessResponse delete(Integer id) {
        this.validateInformedId(id);
        if(this.productService.existsBySupplierId(id)) {
            throw new ValidationException("You can't delete this Supplier because it's already defined by a Product.");
        }
        this.supplierRepository.deleteById(id);
        return SuccessResponse.create("The Supplier was deleted.");
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("The Supplier's ID must be informed.");
        }
    }

    private void validateSupplierNameInformed(SupplierRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The Supplier's name was not informed.");
        }
    }
}
