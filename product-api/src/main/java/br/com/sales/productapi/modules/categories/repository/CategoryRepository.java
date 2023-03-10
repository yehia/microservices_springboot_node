package br.com.sales.productapi.modules.categories.repository;

import br.com.sales.productapi.modules.categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
