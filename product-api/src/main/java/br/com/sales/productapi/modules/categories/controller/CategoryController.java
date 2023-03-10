package br.com.sales.productapi.modules.categories.controller;

import br.com.sales.productapi.modules.categories.dto.CategoryRequest;
import br.com.sales.productapi.modules.categories.dto.CategoryResponse;
import br.com.sales.productapi.modules.categories.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request) {
        return this.categoryService.save(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return this.categoryService.findAll();
    }

    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return this.categoryService.findByIdResponse(id);
    }

    @GetMapping("description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description) {
        return this.categoryService.findByDescription(description);
    }
}
