package com.example.EcommerceFullstack.repository;

import com.example.EcommerceFullstack.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
