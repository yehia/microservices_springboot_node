package br.com.sales.productapi.modules.categories.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.config.helpers.SuccessResponse;
import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.model.Category;
import br.com.sales.productapi.modules.categories.repository.CategoryRepository;
import br.com.sales.productapi.modules.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(this.findById(id));
    }

    public List<CategoryResponse> findAll() {
        return this.categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description) {
        if(isEmpty(description)) {
            throw new ValidationException(("The Category's description must be informed."));
        }
        return this.categoryRepository.findByDescriptionIgnoreCaseContaining(description).stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public Category findById(Integer id) {
        this.validateInformedId(id);
        return this.categoryRepository.findById(id).orElseThrow(() -> new ValidationException("There's no Category for the given ID"));
    }

    public CategoryResponse save(CategoryRequest request) {
        this.validateCategoryDescriptionInformed(request);
        var category = this.categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id) {
        this.validateCategoryDescriptionInformed(request);
        this.validateInformedId(id);
        var category = Category.of(request);
        category.setId(id);
        this.categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(Integer id) {
        this.validateInformedId(id);
        if(this.productService.existsBySupplierId(id)) {
            throw new ValidationException("You can't delete this Supplier because it's already defined by a Category.");
        }
        this.categoryRepository.deleteById(id);
        return SuccessResponse.create("The Category was deleted.");
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("The Category's ID must be informed.");
        }
    }

    private void validateCategoryDescriptionInformed(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The Category's description was not informed.");
        }
    }
}
