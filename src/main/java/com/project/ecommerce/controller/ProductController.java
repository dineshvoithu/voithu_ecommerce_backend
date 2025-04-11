package com.project.ecommerce.controller;

import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔹 Search products by query (put this BEFORE getProductById)
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }

    // 🔹 Get all products (For Customers)
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 🔹 Get products by category (e.g., Mobiles, Tablets)
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        System.out.println("Fetching products for category: " + category);
        return productService.getProductsByCategory(category);
    }

    // 🔹 Get product by ID (for product details page)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
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
