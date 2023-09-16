package com.sangvndo.shoppingcart.repository;

import com.sangvndo.shoppingcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select * from cart_items i where i.product_id = :id", nativeQuery = true)
    CartItem findByProductId(int id);
}
