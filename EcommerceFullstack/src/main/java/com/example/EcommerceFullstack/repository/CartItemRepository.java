package com.example.EcommerceFullstack.repository;

import com.example.EcommerceFullstack.entity.CartItem;
import com.example.EcommerceFullstack.entity.Product;
import com.example.EcommerceFullstack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
