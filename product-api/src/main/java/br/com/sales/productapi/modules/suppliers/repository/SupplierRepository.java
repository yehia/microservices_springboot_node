package br.com.sales.productapi.modules.suppliers.repository;

import br.com.sales.productapi.modules.suppliers.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
