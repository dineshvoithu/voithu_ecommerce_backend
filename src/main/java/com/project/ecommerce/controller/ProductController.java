package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔹 Get all products (For Customers)
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 🔹 Add a product with image (For Seller)
    @PostMapping("/add")
    @PreAuthorize("hasRole('SELLER')")
    public Product addProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile imageFile
    ) {
        return productService.addProductWithImage(name, description, price, category, imageFile);
    }

    // 🔹 Update product
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    // 🔹 Delete product
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    // 🔹 View seller’s own products

    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public List<Product> getSellerProducts() {
        return productService.getProductsBySeller();
    }
}
