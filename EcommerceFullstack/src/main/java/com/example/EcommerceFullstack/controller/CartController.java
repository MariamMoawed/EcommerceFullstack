package com.example.EcommerceFullstack.controller;

import com.example.EcommerceFullstack.entity.CartItem;
import com.example.EcommerceFullstack.service.CartService;
import com.example.EcommerceFullstack.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<CartItem>>> getCart(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Cart loaded", cartService.getUserCart(username)));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseWrapper<CartItem>> addToCart(Authentication auth,
                                                               @RequestParam Long productId,
                                                               @RequestParam int quantity) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Item added",
                cartService.addToCart(auth.getName(), productId, quantity)));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ResponseWrapper<String>> removeFromCart(Authentication auth,
                                                                  @RequestParam Long productId) {
        cartService.removeFromCart(auth.getName(), productId);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Item removed", null));
    }
}
