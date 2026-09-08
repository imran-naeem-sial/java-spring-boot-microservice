package com.app.ecom.Repository;

import com.app.ecom.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findAllByIsActiveIsTrue();

    @Query(value = "select * from product p where p.is_active is true and p.quantity > 0 and lower(p.name) like concat('%',lower(?1),'%')", nativeQuery = true)
    List<Product> searchProducts(String keyword);
}
