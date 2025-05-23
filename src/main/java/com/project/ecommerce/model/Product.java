package com.project.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    // Image Seller Start
    private Long sellerId; // Which seller added this product

    @Column(name = "image_name")
    private String imageName;

    // Image Seller End

    // This will store the URL or path to the product image
    private String imageUrl;
    private String category;
    @ManyToOne
    @JoinColumn(name = "created_by")  // This will be a foreign key to 'users' table
    private User createdBy;
}
