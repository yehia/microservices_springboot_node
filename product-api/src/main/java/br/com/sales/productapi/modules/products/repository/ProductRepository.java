package br.com.sales.productapi.modules.products.repository;

import br.com.sales.productapi.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameIgnoreCaseContaining(String name);
    List<Product> findByCategoryId(Integer id);
    List<Product> findBySupplierId(Integer id);
}
