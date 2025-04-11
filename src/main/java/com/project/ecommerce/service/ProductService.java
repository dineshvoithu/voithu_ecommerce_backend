package com.project.ecommerce.service;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔹 View all products (for Customers)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // 🔹 Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // 🔹 Get products by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                query, query, query);

    }

    // 🔹 Add product with image (for Seller)
    public Product addProductWithImage(String name, String description, double price, String category, MultipartFile imageFile) {
        try {
            // 1⃣ Get current authenticated user (Seller)
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email;

            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 2⃣ Define upload directory (relative to project folder)
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // ✅ Create the folder if it doesn't exist
            }

            // 3⃣ Save the image file
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, imageFile.getBytes());

            // 4⃣ Create and save the product
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setImageUrl("/uploads/" + fileName); // Optional: path for frontend access
            product.setCreatedBy(user);

            return productRepository.save(product);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    // 🔹 Update product (for Seller)
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());

        return productRepository.save(existingProduct);
    }

    // 🔹 Delete product (for Seller)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    // 🔹 View only own products (for Seller)
    public List<Product> getProductsBySeller() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productRepository.findByCreatedBy(user);
    }
}
