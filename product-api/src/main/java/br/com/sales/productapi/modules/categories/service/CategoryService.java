package br.com.sales.productapi.modules.categories.service;

import br.com.sales.productapi.config.exception.ValidationException;
import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.model.Category;
import br.com.sales.productapi.modules.categories.repository.CategoryRepository;
import br.com.sales.productapi.modules.suppliers.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Integer id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new ValidationException("There's no Category for the given ID"));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryDescriptionInformed(request);
        var category = this.categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryDescriptionInformed(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The Category's description was not informed.");
        }
    }
}
