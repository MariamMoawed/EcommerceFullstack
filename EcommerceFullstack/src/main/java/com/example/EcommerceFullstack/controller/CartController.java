package com.example.EcommerceFullstack.controller;

import com.example.EcommerceFullstack.entity.CartItem;
import com.example.EcommerceFullstack.service.CartService;
import com.example.EcommerceFullstack.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<CartItem>>> getCart(Authentication auth) {
        // Cast auth.getPrincipal() to UserDetails to access the username
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Cart loaded", cartService.getUserCart(username)));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseWrapper<CartItem>> addToCart(Authentication auth,
                                                               @RequestParam Long productId,
                                                               @RequestParam int quantity) {
        // Cast auth.getPrincipal() to UserDetails to access the username
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Item added",
                cartService.addToCart(username, productId, quantity)));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ResponseWrapper<String>> removeFromCart(Authentication auth,
                                                                  @RequestParam Long productId) {
        // Cast auth.getPrincipal() to UserDetails to access the username
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        cartService.removeFromCart(username, productId);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Item removed", null));
    }
}
