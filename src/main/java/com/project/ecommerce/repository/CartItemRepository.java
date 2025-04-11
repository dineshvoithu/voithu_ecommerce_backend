package com.project.ecommerce.repository;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface CartItemRepository extends JpaRepository<CartItem, Long> {
        List<CartItem> findByUser(User user);

    }


