package com.project.ecommerce.controller;

import com.project.ecommerce.model.AddToCartRequest;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestBody AddToCartRequest request,
            Principal principal
    ) {
        // 🔹 Get logged-in user's email
        String email = principal.getName();

        // 🔹 Fetch User by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Add item to cart
        CartItem cartItem = cartService.addToCart(
                request.getProductId(),
                request.getQuantity(),
                user
        );

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
//Get
    @GetMapping
    public ResponseEntity<?> getCartItems(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(cartService.getCartItems(user));
    }
//    Update Quantity
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody AddToCartRequest request,
            Principal principal) {

        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartItem updatedItem = cartService.updateCartItem(cartItemId, request.getQuantity(), user);
        return ResponseEntity.ok(updatedItem);
    }

//    DELETE
@DeleteMapping("/delete/{cartItemId}")
public ResponseEntity<?> deleteCartItem(
        @PathVariable Long cartItemId,
        Principal principal) {

    String email = principal.getName();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    cartService.deleteCartItem(cartItemId, user);
    return ResponseEntity.ok("Item removed from cart.");
}

}
