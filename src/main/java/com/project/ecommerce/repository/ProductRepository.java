package com.project.ecommerce.repository;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 🔹 Get all products added by a specific seller
    List<Product> findByCreatedBy(User user);
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name, String description, String category);


}
