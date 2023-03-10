package br.com.sales.productapi.modules.suppliers.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.model.Category;
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
        if(isEmpty(id)) {
            throw new ValidationException("The Supplier's ID was not informed");
        }
        return this.supplierRepository.findById(id).orElseThrow(() -> new ValidationException("There's no Supplier for the given ID"));
    }

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier = this.supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierNameInformed(SupplierRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The Supplier's name was not informed.");
        }
    }
}
