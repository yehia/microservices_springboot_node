package br.com.sales.productapi.modules.products.repository;

import br.com.sales.productapi.modules.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
