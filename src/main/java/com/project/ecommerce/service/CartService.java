package com.project.ecommerce.service;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.CartItemRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItem addToCart(Long productId, int quantity, User user) {
        // 🔹 Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 🔹 Check if product already exists in user's cart
        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItem != null) {
            // Product already in cart — just update quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        }
        // 🔹 Create new CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }
//GET
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }
//    Update Quantity
    public CartItem updateCartItem(Long cartItemId, int newQuantity, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this cart item");
        }

        cartItem.setQuantity(newQuantity);
        return cartItemRepository.save(cartItem);
    }

//    DELETE
public void deleteCartItem(Long cartItemId, User user) {
    CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

    if (!cartItem.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("Unauthorized to delete this cart item");
    }

    cartItemRepository.delete(cartItem);
}

}
