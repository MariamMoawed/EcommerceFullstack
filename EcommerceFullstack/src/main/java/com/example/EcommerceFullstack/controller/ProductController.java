package com.example.EcommerceFullstack.controller;

import com.example.EcommerceFullstack.entity.Product;
import com.example.EcommerceFullstack.repository.CategoryRepository;
import com.example.EcommerceFullstack.service.ProductService;
import com.example.EcommerceFullstack.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Product>>> all() {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "All products", productService.getAll()));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<Product>> create(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam Long categoryId,
            @RequestPart MultipartFile image) throws IOException {

        String filePath = "uploads/" + image.getOriginalFilename();
        Files.copy(image.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(filePath);
        product.setCategory(categoryRepository.findById(categoryId).orElseThrow());

        return ResponseEntity.ok(new ResponseWrapper<>(true, "Created", productService.save(product)));
    }

    @GetMapping("/recommend/{categoryId}")
    public ResponseEntity<ResponseWrapper<List<Product>>> recommend(@PathVariable Long categoryId) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Recommended", productService.recommend(categoryId)));
    }
}
