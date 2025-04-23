package com.example.EcommerceFullstack.service;

import com.example.EcommerceFullstack.entity.Product;
import com.example.EcommerceFullstack.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }


    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Product save(Product p) {
        return productRepo.save(p);
    }

    public List<Product> recommend(Long categoryId) {
        return productRepo.findByCategoryId(categoryId);
    }
}
