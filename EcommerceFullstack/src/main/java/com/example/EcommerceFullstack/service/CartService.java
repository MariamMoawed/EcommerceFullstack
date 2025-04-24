package com.example.EcommerceFullstack.service;

import com.example.EcommerceFullstack.entity.CartItem;
import com.example.EcommerceFullstack.entity.Product;
import com.example.EcommerceFullstack.entity.User;
import com.example.EcommerceFullstack.repository.CartItemRepository;
import com.example.EcommerceFullstack.repository.ProductRepository;
import com.example.EcommerceFullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;


    public List<CartItem> getUserCart(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return cartRepo.findByUser(user);
    }

    public CartItem addToCart(String username, Long productId, int quantity) {
        User user = userRepo.findByUsername(username).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        Optional<CartItem> existing = cartRepo.findByUserAndProduct(user, product);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartRepo.save(item);
        }

        CartItem item = new CartItem();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        return cartRepo.save(item);
    }

    public void removeFromCart(String username, Long productId) {
        User user = userRepo.findByUsername(username).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();
        cartRepo.findByUserAndProduct(user, product).ifPresent(cartRepo::delete);
    }
}
