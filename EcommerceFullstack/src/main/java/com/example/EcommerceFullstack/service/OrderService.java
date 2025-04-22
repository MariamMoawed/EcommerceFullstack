package com.example.EcommerceFullstack.service;

import com.example.EcommerceFullstack.entity.CartItem;
import com.example.EcommerceFullstack.entity.Order;
import com.example.EcommerceFullstack.entity.OrderItem;
import com.example.EcommerceFullstack.entity.User;
import com.example.EcommerceFullstack.repository.CartItemRepository;
import com.example.EcommerceFullstack.repository.OrderRepository;
import com.example.EcommerceFullstack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo, CartItemRepository cartRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    public Order placeOrder(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        List<CartItem> cartItems = cartRepo.findByUser(user);

        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");

        Order order = new Order();
        order.setUser(user);
        order.setOrderedAt(LocalDateTime.now());

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        Order saved = orderRepo.save(order);

        cartRepo.deleteAll(cartItems);

        return saved;
    }

    public List<Order> getOrders(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return orderRepo.findByUser(user);
    }
}
