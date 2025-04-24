package com.example.EcommerceFullstack.controller;

import com.example.EcommerceFullstack.entity.Order;
import com.example.EcommerceFullstack.service.OrderService;
import com.example.EcommerceFullstack.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/checkout")
    public ResponseEntity<ResponseWrapper<Order>> checkout(Authentication auth) {
        // Cast auth.getPrincipal() to UserDetails to access the username
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Order placed",
                orderService.placeOrder(username)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Order>>> orders(Authentication auth) {
        // Cast auth.getPrincipal() to UserDetails to access the username
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Orders", orderService.getOrders(username)));
    }
}

