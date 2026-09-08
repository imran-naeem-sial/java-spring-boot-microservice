package com.app.ecom.Repository;

import com.app.ecom.Model.CartItem;
import com.app.ecom.Model.Product;
import com.app.ecom.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findFirstByUserAndProduct(User user, Product product);
    List<CartItem> findAllByUser(User user);

    @Modifying
    @Transactional
    int deleteByUser(User user);
}
